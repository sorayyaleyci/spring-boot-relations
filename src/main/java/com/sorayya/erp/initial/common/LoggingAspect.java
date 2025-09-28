package com.sorayya.erp.initial.common;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    @Pointcut("execution(* com.sorayya.erp.initial.services.*.*(..))")
    public void serviceMethods() {}

    @Before("serviceMethods()")
    public void logBefore(JoinPoint joinPoint) {
        System.out.println("Before method: " + joinPoint.getSignature().getName());
    }

    @AfterReturning(pointcut = "serviceMethods()", returning = "result")
    public void logAfter(JoinPoint joinPoint, Object result) {
        System.out.println("After method: " + joinPoint.getSignature().getName() +
                " | Returned: " + result);
    }
    @Around("serviceMethods()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        try {
            Object result = joinPoint.proceed();
            long duration = System.currentTimeMillis() - start;
            System.out.println("⏱ Executed: " + joinPoint.getSignature().toShortString() +
                    " in " + duration + " ms");
            return result;
        } catch (Exception ex) {
            System.out.println("Exception in: " + joinPoint.getSignature().toShortString() +
                    " | " + ex.getMessage());
            throw ex;
        }
    }

}

