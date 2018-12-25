package test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import test.pojo.model.SocketMessage;
import test.service.SocketMessageService;

import java.util.List;

@RestController
@RequestMapping("/api/socketmessage")
public class SocketMessageController {
    @Autowired
    private SocketMessageService socketMessageService;

    @GetMapping("")
    public List<SocketMessage> getAll() {
        return socketMessageService.getAll();
    }
}
