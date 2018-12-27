package test.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class FileService {
    private String filedir; //文件路径

    @Value("${filedir.userfiles}")
    private String userfilesdir;

    @Value("${filedir.linux}")
    private String linuxdir;

    @Value("${filedir.windows}")
    private String windowsdir;

    private void setDir(){
        if(System.getenv("OS").equals("Windows_NT")) {
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
        String dateString = sf.format(new Date());

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
        return "/" + context.getAuthentication().getName() + "/" + dateString  + "/" + fileName;
    }

    /**
     * 文件下载功能
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
}
