package test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import test.dao.FileDao;
import test.pojo.dto.UserFile;
import test.pojo.dto.mapper.ServerUserFileMapper;
import test.pojo.entity.ServerFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class FileService {
    private String filedir; //文件路径

    @Value("${filedir.userfiles}")
    private String userfilesdir;

    @Value("${filedir.linux}")
    private String linuxdir;

    @Value("${filedir.windows}")
    private String windowsdir;

    @Autowired
    private FileService fileService;
    @Autowired
    private ServerUserFileMapper serverUserFileMapper;
    @Autowired
    private FileDao fileDao;
    @Autowired
    private UserService userService;

    private void setDir(){
        if("Windows_NT".equals(System.getenv("OS"))) {
            filedir = windowsdir + userfilesdir;
        } else {
            filedir = linuxdir + userfilesdir;
        }
    }
    /**
     * 文件上传功能
     *
     * @param file
     * @return
     * @throws IOException
     */

    public String upload(MultipartFile file) throws IOException {
        setDir();
        SecurityContext context = SecurityContextHolder.getContext();

        SimpleDateFormat sf = new SimpleDateFormat("yyyy_MM_dd");
        Date uploadDate = new Date();
        String dateString = sf.format(uploadDate);

        String path = filedir + "/" + context.getAuthentication().getName() + "/" + dateString ;
        String fileName = file.getOriginalFilename();
        File dir = new File(path, fileName);
        if (!dir.getParentFile().exists()) {
            dir.getParentFile().mkdirs();
            if(!dir.exists()) {
                dir.createNewFile();
            }
        }
        //MultipartFile自带的解析方法
        file.transferTo(dir);
        String fullDir = "/" + context.getAuthentication().getName() + "/" + dateString  + "/" + fileName;

        ServerFile serverFile = new ServerFile();
        serverFile.setName(fileName);
        serverFile.setPath(fullDir);
        serverFile.setUser(userService.getAuthUser());
        serverFile.setDate(uploadDate);
        fileDao.save(serverFile);

        return fullDir;
    }

    /**
     * 文件下载功能，存在性能问题，暂时用静态文件路径形式代替
     *
     * @param response
     * @throws Exception
     */
    public void download(HttpServletResponse response, String filename) throws IOException {
        setDir();

        String url = filedir + "/" + filename;
        //获取输入流
        InputStream bis = new BufferedInputStream(new FileInputStream(new File(url)));
        //假如以中文名下载的话
        filename = URLEncoder.encode(filename, "UTF-8");
        //设置文件下载头
        response.addHeader("Content-Disposition", "attachment;filename=" + filename);
        //1.设置文件ContentType类型，这样设置，会自动判断下载文件类型
        response.setContentType("multipart/form-data");
        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
        int len = 0;
        while ((len = bis.read()) != -1) {
            out.write(len);
            out.flush();
        }
        bis.close();
        out.close();
    }

    /**
     * 获取用户文件列表
     */
    public List<UserFile> getUserFile() {
        List<UserFile> userFiles = new ArrayList<>();
        fileDao.findByUser(userService.getAuthUser()).forEach( file -> {
            userFiles.add(serverUserFileMapper.map(file));
        });
        return userFiles;
    }

    /**
     * 删除文件
     */
    public Boolean delete(Integer id) {
        setDir();
        ServerFile serverFile = fileDao.findById(id).get();
        File file = new File(filedir + serverFile.getPath());
        if(file.exists()){
            fileDao.delete(serverFile);
            return file.delete();
        } else {
            return false;
        }
    }
}
