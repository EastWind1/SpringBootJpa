package test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import test.pojo.entity.User;
import test.service.UserService;
import javax.persistence.EntityManager;

/**
 * 用户API控制器
 */

@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 前端检测是否登录
     *
     * @return
     */
    @GetMapping("")
    @ResponseBody
    public Boolean isLogin() {
        // 已启用spring security，仅return
        return true;
    }

    /**
     * 注册
     * @param username
     * @param password
     * @return
     * @throws Exception
     */
    @PostMapping("")
    @ResponseBody
    public User signup(String username, String password) throws Exception {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        if (user.getUsername() == null || user.getPassword() == null) {
            throw new Exception("用户名或密码为空");
        }
        return userService.add(user);
    }
}
