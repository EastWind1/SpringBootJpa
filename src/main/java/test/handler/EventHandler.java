package test.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import test.pojo.event.MyTestEvent;

/**
 * 事件处理
 */
@Async
@Component
public class EventHandler {

    private Logger logger = LoggerFactory.getLogger(MyTestEvent.class);

    @EventListener()
    public void onMyTestEvent(MyTestEvent myTestEvent) {
        logger.info("I got "+myTestEvent.message);
    }
}
