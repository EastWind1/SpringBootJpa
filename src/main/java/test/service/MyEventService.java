package test.service;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import test.event.MyTestEvent;

@Service
@Async
public class MyEventService {
    @EventListener
    public void onMyTestEvent(MyTestEvent myTestEvent) {
        System.out.println("I got "+myTestEvent.message);
    }
}
