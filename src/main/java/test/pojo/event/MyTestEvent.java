package test.pojo.event;

import org.springframework.context.ApplicationEvent;

/**
 * 测试事件
 */
public class MyTestEvent extends ApplicationEvent {
    
    public String message = "";
    
    public MyTestEvent(Object source, String message) {
        super(source);
        this.message = message;
    }
}
