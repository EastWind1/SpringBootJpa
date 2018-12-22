package test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;
import test.pojo.event.MyTestEvent;

@RestController
@RequestMapping("/api/event")
public class EventController {
    @Autowired
    private ApplicationContext context; // 应用上下文

    @GetMapping("")
    @ResponseBody
    public void publishEvent(String message) {
        MyTestEvent event = new MyTestEvent(this,message);
        context.publishEvent(event);
    }
}
