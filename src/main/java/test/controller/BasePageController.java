package test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 基础页面跳转配置
 */

@Controller
@RequestMapping("")
public class BasePageController {
    @GetMapping("/login")
    public String login(){
        return "login.html";
    }
    @GetMapping("/signup")
    public String signup(){
        return "signup.html";
    }
    @GetMapping("/socket")
    public String socket(){
        return "socket.html";
    }
}
