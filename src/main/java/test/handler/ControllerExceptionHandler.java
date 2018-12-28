package test.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import test.pojo.common.RestResult;

@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler
    @ResponseBody
    @ResponseStatus
    public RestResult handleAll(Exception e) {
        RestResult restResult = new RestResult();
        restResult.setStatus(false);
        restResult.setContent(e.getMessage());
        return restResult;
    }
}
