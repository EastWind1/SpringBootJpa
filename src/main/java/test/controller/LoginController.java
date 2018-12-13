package test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import test.pojo.Users;
import test.service.UsersService;

import javax.persistence.EntityManager;
import java.util.List;

@RestController
@RequestMapping("/users")
public class LoginController {
    @Autowired
    private UsersService usersService;
    @Autowired
    private EntityManager entityManager;

    @GetMapping("")
    @ResponseBody
    public List<Users> getAll(){
    //        QUsers qUsers = QUsers.users;
    //        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
    //        return jpaQueryFactory.select(qUsers)
    //                .from(qUsers)
    //                .where(qUsers.username.startsWith("a"))
    //                .fetch();
    return usersService.getAllUsers();
    }
}
