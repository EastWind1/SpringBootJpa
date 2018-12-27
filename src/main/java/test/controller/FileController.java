package test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import test.service.FileService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/api/file")
public class FileController {
    @Autowired
    private FileService fileService;

    @PostMapping("")
    @ResponseBody
    public String upload(MultipartFile file) throws IOException {
        return "\"" + fileService.upload(file) + "\"";
    }

    @GetMapping("")
    public void downLoad(HttpServletResponse response, String filedir) throws IOException {
        fileService.download(response,filedir);
    }
}
