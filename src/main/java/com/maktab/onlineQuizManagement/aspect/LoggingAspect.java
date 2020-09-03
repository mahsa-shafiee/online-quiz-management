package com.maktab.onlineQuizManagement.aspect;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Log4j2
public class LoggingAspect {

    @Around("within(com.maktab.onlineQuizManagement.*.*)")
    public Object loggingAdvice(ProceedingJoinPoint proceedingJoinPoint) {
        Object returnValue = null;

        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();

        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();

        log.info("Method '" + methodName + "' of class '" + className + "' started");
        try {

            returnValue = proceedingJoinPoint.proceed();
            log.info("Method '" + methodName + "' of class '" + className + "' successfully finished");

        } catch (Throwable throwable) {

            log.error("Method '" + methodName + "' of class '" + className + "' threw exception: " + throwable.toString());
        }
        return returnValue;
    }

}
