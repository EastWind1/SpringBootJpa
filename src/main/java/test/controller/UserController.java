package test.controller;

import jdk.nashorn.internal.objects.annotations.Constructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import test.pojo.entity.User;
import test.service.UserService;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private EntityManager entityManager;


    @GetMapping("")
    @ResponseBody
    public List<User> getAll() {
        //        QUser qUsers = QUser.users;
        //        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        //        return jpaQueryFactory.select(qUsers)
        //                .from(qUsers)
        //                .where(qUsers.username.startsWith("a"))
        //                .fetch();
        return userService.getAllUsers();
    }

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
