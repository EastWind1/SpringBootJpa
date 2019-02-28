package test.handler;

import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public String handlerAll(Exception e) {
        e.printStackTrace();
        return e.getMessage();
    }
}
