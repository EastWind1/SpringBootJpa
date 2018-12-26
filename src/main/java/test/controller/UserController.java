package test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import test.pojo.entity.User;
import test.service.UserService;

import javax.persistence.EntityManager;

/**
 * 用户相关控制器
 */

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private EntityManager entityManager;

    /**
     * 前端检测是否登录，使用Spring Security拦截
     * @return
     */
    @GetMapping("")
    @ResponseBody
    public Boolean isLogin() {
        //        QUser qUsers = QUser.users;
        //        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        //        return jpaQueryFactory.select(qUsers)
        //                .from(qUsers)
        //                .where(qUsers.username.startsWith("a"))
        //                .fetch();
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
        if (!username.isEmpty() && !password.isEmpty()) {
            user.setUsername(username);
            user.setPassword(password);
        }
        return userService.add(user);
    }
}
