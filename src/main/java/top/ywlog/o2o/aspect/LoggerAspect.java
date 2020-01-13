package top.ywlog.o2o.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * Author: Durian
 * Date: 2020/1/7 12:47
 * Description: AOP注入日志
 */
@Component
@Aspect
public class LoggerAspect
{
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggerAspect.class);

    // 定义serviceImpl切面
    @Pointcut("execution(* top.ywlog.o2o.service.impl.*.*(..))")
    public void loggerAspectToServiceImpl() {}

    // 定义web切面
    @Pointcut("execution(* top.ywlog.o2o.web.*.*.*(..))")
    public void loggerAspectToWeb() {}

    @Around("loggerAspectToWeb()")
    public Object printLogAroundToWeb(ProceedingJoinPoint pjp)
    {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        Object rtValue = null;
        long startTime = 0L;
        long endTime = 0L;
        try
        {
            HttpServletRequest request = requestAttributes.getRequest();
            // 拦截方法执行前
            // 打印请求内容
            LOGGER.info("===============请求内容===============");
            LOGGER.info("请求地址:" + request.getRequestURL().toString());
            LOGGER.info("请求方式:" + request.getMethod());
            LOGGER.info("请求类方法:" + pjp.getSignature());
            LOGGER.info("请求类方法参数:" + Arrays.toString(pjp.getArgs()));
            LOGGER.info("===============请求内容===============");
            // 执行被拦截的方法
            Object[] args = pjp.getArgs();
            startTime = System.currentTimeMillis();
            rtValue = pjp.proceed(args);
            endTime = System.currentTimeMillis();
        } catch (Throwable e)
        {
            // 发生异常时
            LOGGER.error(pjp.getSignature().getName() + "执行失败:" + e.getMessage());
        }
        // 方法正常执行完毕后
        LOGGER.info(pjp.getSignature().getName() + "执行完毕，耗时:" + (endTime - startTime) + "ms");
        LOGGER.info("--------------返回内容----------------");
        LOGGER.info("Response内容:" + rtValue);
        LOGGER.info("--------------返回内容----------------");
        return rtValue;
    }

    @Around("loggerAspectToServiceImpl()")
    public Object printLogAroundToService(ProceedingJoinPoint pjp)
    {
        long startTime = 0L;
        long endTime = 0L;
        Object rtValue = null;
        Object[] args = pjp.getArgs();
        try
        {
            LOGGER.info("执行类方法:" + pjp.getSignature());
            LOGGER.info("执行类方法参数:" + Arrays.toString(pjp.getArgs()));
            startTime = System.currentTimeMillis();
            rtValue = pjp.proceed(args);
            endTime = System.currentTimeMillis();
        } catch (Throwable e)
        {
            LOGGER.error(pjp.getSignature().getName() + "执行失败:" + e.getMessage());
        }
        LOGGER.info(pjp.getSignature().getName() + "执行完毕，耗时:" + (endTime - startTime) + "ms");
        LOGGER.info("--------------返回内容----------------");
        LOGGER.info("返回内容:" + rtValue);
        LOGGER.info("--------------返回内容----------------");
        return rtValue;
    }
}
