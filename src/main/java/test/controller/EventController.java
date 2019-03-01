package test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;
import test.pojo.event.MyTestEvent;

/**
 * 事件调试控制器
 */

@RestController
@RequestMapping("/api/event")
public class EventController {
    private final ApplicationContext context; // 应用上下文

    @Autowired
    public EventController(ApplicationContext context) {
        this.context = context;
    }

    @GetMapping("")
    @ResponseBody
    public void publishEvent(String message) {
        MyTestEvent event = new MyTestEvent(this,message);
        context.publishEvent(event);
    }
}
