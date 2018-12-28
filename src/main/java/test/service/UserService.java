package test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import test.dao.UserDao;
import test.pojo.entity.User;

import java.util.List;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserDao userDao;

    @Cacheable(cacheNames = "'usercache'")
    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userDao.findByUsername(s);
        if (user == null) {
            throw new UsernameNotFoundException("no user");
        }
        return user;
    }

    @Transactional
    public User add(User user) throws Exception {
        if(userDao.findByUsername(user.getUsername()) != null) {
            throw new Exception("user exist");
        }

        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        return userDao.save(user);
    }

    public User getAuthUser(){
        SecurityContext context = SecurityContextHolder.getContext();
        return userDao.findByUsername(context.getAuthentication().getName());
    }
}
