package test.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * 日志记录配置
 * 使用切面实现
 */

@Aspect
@Component
public class LogConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(LogConfiguration.class);
    private static SecurityContext context;

    /**
     * 控制器切入
     */

    @Pointcut("execution(public * test.controller..*(..))") //切入控制器
    public void logController() {
    }

    @Before("logController()")
    public void controllerDoBefore(JoinPoint joinPoint) throws Throwable {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        context = SecurityContextHolder.getContext(); // 获取Security上下文
        // 记录请求内容
        logger.info("Got request: { User: " + context.getAuthentication().getName()
                    + ". Url: " + request.getRequestURL().toString()
                    + ". Type: " + request.getMethod()
                    + ". Method : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName()
                    + ". Args : " + Arrays.toString(joinPoint.getArgs()) + " }");
    }

//    // 请求计时
//    @Around("logPointCut()")
//    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
//        long startTime = System.currentTimeMillis();
//        Object ob = pjp.proceed();// ob 为方法的返回值
//        logger.info("Time : " + (System.currentTimeMillis() - startTime) + "ms");
//        return ob;
//    }

    /**
     * 服务切入
     */
    @Pointcut("execution(public * test.service..*(..))") //切入服务
    public void logService() {
    }


    @Before("logService()")
    public void serviceDoBefore(JoinPoint joinPoint) throws Throwable {

        // 记录内容
        logger.info("Service invoke: { "
                + "Method : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName()
                + ". Args : " + Arrays.toString(joinPoint.getArgs()) + " }");
    }
    @AfterReturning(returning = "returnValue", pointcut = "logService()")
    public void doAfterReturning(Object returnValue) throws Throwable {
        logger.info("Service return: { " + returnValue + " }");
    }

}
