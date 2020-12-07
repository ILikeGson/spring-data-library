package com.example.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
public class ServiceLoggingAspect {
    private static final Logger LOG = LoggerFactory.getLogger(ServiceLoggingAspect.class);
    private static final String CLASS_NAME = "\nClass name: %s";
    private static final String METHOD_NAME = "Method name: %s";
    private static final String ARGS = "Args: %s\n";

    @Before("execution(* com.example.service.*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        LOG.info(String.format(CLASS_NAME, joinPoint.getTarget().getClass().getSimpleName()));
        LOG.info(String.format(METHOD_NAME, joinPoint.getSignature().getName()));
        LOG.info(String.format(ARGS, Arrays.toString(joinPoint.getArgs())));
    }

}
