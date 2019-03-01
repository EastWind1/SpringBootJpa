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
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 文件服务
 */
@Service
public class FileService {

    @Value("${filedir.userfiles}")
    private String userfilesdir; // 用户文件路径

    @Value("${filedir.linux}")
    private String linuxdir; // linux静态资源路径

    @Value("${filedir.windows}")
    private String windowsdir; // windows静态资源路径

    private final ServerUserFileMapper serverUserFileMapper;
    private final FileDao fileDao;
    private final UserService userService;

    @Autowired
    public FileService(ServerUserFileMapper serverUserFileMapper, FileDao fileDao, UserService userService) {
        this.serverUserFileMapper = serverUserFileMapper;
        this.fileDao = fileDao;
        this.userService = userService;
    }

    private String getDir(){
        if("Windows_NT".equals(System.getenv("OS"))) {
            return windowsdir + userfilesdir;
        } else {
            return linuxdir + userfilesdir;
        }
    }
    /**
     * 文件上传功
     */

    public String upload(MultipartFile file) throws IOException {
        final String filedir = getDir();
        SecurityContext context = SecurityContextHolder.getContext();

        SimpleDateFormat sf = new SimpleDateFormat("yyyy_MM_dd");
        Date uploadDate = new Date();
        String dateString = sf.format(uploadDate);

        String path = filedir + "/" + context.getAuthentication().getName() + "/" + dateString ;
        String fileName = file.getOriginalFilename();
        File dir = null;
        if (fileName != null) {
            dir = new File(path, fileName);
        }
        if (dir != null && !dir.getParentFile().exists()) {
            if (!dir.getParentFile().mkdirs()) {
                throw new IOException("目录创建失败");
            }
            if(!dir.exists()) {
                if (!dir.createNewFile()) {
                    throw new IOException("文件创建失败");
                }
            }

            file.transferTo(dir); //MultipartFile自带的解析方法
        }

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
     * 文件下载功能
     * TODO:存在性能问题，暂时用静态文件路径形式代替
     */
    public void download(HttpServletResponse response, String filename) throws IOException {
        final String filedir = getDir();

        String url = filedir + "/" + filename;
        //获取输入流
        InputStream bis = new BufferedInputStream(new FileInputStream(new File(url)));
        //假如以中文名下载的话
        filename = URLEncoder.encode(filename, StandardCharsets.UTF_8);
        //设置文件下载头
        response.addHeader("Content-Disposition", "attachment;filename=" + filename);
        //1.设置文件ContentType类型，这样设置，会自动判断下载文件类型
        response.setContentType("multipart/form-data");
        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
        int len;
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
        fileDao.findByUser(userService.getAuthUser()).forEach( file -> userFiles.add(serverUserFileMapper.map(file)));
        return userFiles;
    }

    /**
     * 删除文件
     */
    public Boolean delete(Integer id) {
        final String filedir = getDir();

        var fileOptional = fileDao.findById(id);
        ServerFile serverFile = null;
        if (fileOptional.isPresent()) {
            serverFile = fileOptional.get();
        }

        File file = new File(filedir + Objects.requireNonNull(serverFile).getPath());
        if(file.exists()){
            fileDao.delete(serverFile);
            return file.delete();
        } else {
            return false;
        }
    }
}
