package test.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.dao.MessageDao;
import test.dao.UserDao;
import test.pojo.entity.Message;
import test.pojo.entity.User;
import test.pojo.dto.SocketMessage;
import test.pojo.dto.mapper.SocketMessageMapper;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class SocketMessageService {
    @Autowired
    private SocketMessageMapper socketMessageMapper;
    @Autowired
    private UserDao userDao;
    @Autowired
    private MessageDao messageDao;

    public List<SocketMessage> getAll() {
        List<Message> messages = messageDao.findAll();
        List<SocketMessage> result = new ArrayList<>();
        messages.forEach( message -> {
            User user = userDao.findById(message.getUser().getId()).get();
            result.add(socketMessageMapper.map(user, message));
        });
        return result;
    }
}
