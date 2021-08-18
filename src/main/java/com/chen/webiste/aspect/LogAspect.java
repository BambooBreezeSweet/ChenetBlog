/**
 * FileName: LogAspect
 * Author:   嘉平十七
 * Date:     2021/6/18 13:46
 * Description: 日志配置
 */
package com.chen.webiste.aspect;

import com.chen.webiste.utils.IPUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
@Component
public class LogAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(* com.chen.webiste.controller.*.*(..))")
    public void log(){

    }

    @Before("log()")
    public void doBefore(JoinPoint joinPoint){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String url = request.getRequestURL().toString();
        String localIp = request.getLocalAddr();
        String remoteIp = IPUtils.getIpAddr(request);
        String classMethod = joinPoint.getSignature().getDeclaringTypeName()+":"+joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        RequestLog requestLog = new RequestLog(url, localIp, remoteIp, classMethod, args);
        logger.info("-----doBefore-----");
        logger.info(requestLog.toString());
    }

    @After("log()")
    public void doAfter(){
        logger.info("-----doAfter-----");
    }

    @AfterReturning(returning = "result",pointcut = "log()")
    public void doAfterReturning(Object result){
        logger.info("-----doAfterReturning-----");
        logger.info("Result : {}",result);
    }

    private static class RequestLog{
        private final String url;
        private final String localIp;
        private final String remoteIp;
        private final String classMethod;
        private final Object[] args;

        public RequestLog(String url, String localIp, String remoteIp, String classMethod, Object[] args) {
            this.url = url;
            this.localIp = localIp;
            this.remoteIp = remoteIp;
            this.classMethod = classMethod;
            this.args = args;
        }

        @Override
        public String toString() {
            return "RequestLog : {" +
                    "url='" + url + '\'' +
                    ", localIp='" + localIp + '\'' +
                    ", remoteIp='" + remoteIp + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }
}