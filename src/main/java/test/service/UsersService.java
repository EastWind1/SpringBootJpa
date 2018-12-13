package test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import test.dao.UsersDao;
import test.pojo.Users;

import java.util.List;

@Service
@CacheConfig(cacheNames = "users")
public class UsersService {
    @Autowired
    private UsersDao usersDao;

    @Cacheable(key = "'usercache'")
    public List<Users> getAllUsers() {
        return usersDao.findAll();
    }
}
