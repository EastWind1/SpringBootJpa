package test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import test.pojo.dto.UserFile;
import test.service.FileService;
import java.io.IOException;
import java.util.List;

/**
 * 文件API控制器
 */
@Controller
@RequestMapping("/api/file")
public class FileController {
    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("")
    @ResponseBody
    public String upload(MultipartFile file) throws IOException {
        return "\"" + fileService.upload(file) + "\"";
    }

    @GetMapping("")
    @ResponseBody
    public List<UserFile> userFiles() {
        return fileService.getUserFile();
    }

    @DeleteMapping("{id}")
    @ResponseBody
    public Boolean delete(@PathVariable Integer id) {
        return fileService.delete(id);
    }

}
