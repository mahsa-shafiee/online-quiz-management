package com.maktab.onlineQuizManagement.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;

@Component
@Aspect
public class SessionCheckingAspect {

    @Around(value = "(within(com.maktab.onlineQuizManagement.controller.*RestController) || " +
            "within(com.maktab.onlineQuizManagement.controller.AdminController)) && " +
            "args(..,request)",
            argNames = "proceedingJoinPoint,request")
    public Object checkingAdvice(ProceedingJoinPoint proceedingJoinPoint, HttpServletRequest request) throws Throwable {
        HttpSession session = request.getSession(false);
        if (Objects.isNull(session))
            return "error";
        return proceedingJoinPoint.proceed();
    }

}
