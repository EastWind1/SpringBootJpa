package test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import test.event.MyTestEvent;

@RestController
@RequestMapping("/event")
public class EventController {
    @Autowired
    private ApplicationContext context; // 应用上下文

    @PostMapping("")
    @ResponseBody
    public void publishEvent(String message) {
        MyTestEvent event = new MyTestEvent(this,message);
        context.publishEvent(event);
    }
}
