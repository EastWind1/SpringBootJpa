package test.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import test.event.MyTestEvent;

@Service
@Async
public class MyEventService {

    private Logger logger = LoggerFactory.getLogger(MyTestEvent.class);

    @EventListener
    public void onMyTestEvent(MyTestEvent myTestEvent) {
        logger.info("I got "+myTestEvent.message);
    }
}
