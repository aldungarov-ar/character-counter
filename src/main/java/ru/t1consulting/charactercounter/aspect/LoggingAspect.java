package ru.t1consulting.charactercounter.aspect;

import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.StringJoiner;

@Component
@Aspect
@Log4j2
public class LoggingAspect {
    @Around("ru.t1consulting.charactercounter.aspect.LoggingPointcuts.springBeanPointcut() && " +
            "ru.t1consulting.charactercounter.aspect.LoggingPointcuts.applicationPackagePointcut() && " +
            "ru.t1consulting.charactercounter.aspect.AnnotationPointcuts.info()")
    public Object aroundInfoAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        return around(joinPoint, Level.INFO);
    }

    @Around("ru.t1consulting.charactercounter.aspect.LoggingPointcuts.springBeanPointcut() && " +
            "ru.t1consulting.charactercounter.aspect.LoggingPointcuts.applicationPackagePointcut() && " +
            "ru.t1consulting.charactercounter.aspect.AnnotationPointcuts.debug()")
    public Object aroundDebugAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        return around(joinPoint, Level.DEBUG);
    }

    @AfterThrowing(
            pointcut =
                    "ru.t1consulting.charactercounter.aspect.LoggingPointcuts.springBeanPointcut() && " +
                            "ru.t1consulting.charactercounter.aspect.LoggingPointcuts.applicationPackagePointcut()",
            throwing = "ex")
    public void afterThrowingAdvice(JoinPoint joinPoint, Exception ex) {
        Signature signature = joinPoint.getSignature();

        String method = signature.getName();
        String declaringType = signature.getDeclaringTypeName();

        log.error("Exception in {}.{}(): {}", declaringType, method, ex.getMessage());
    }

    private Object around(ProceedingJoinPoint joinPoint, Level level) throws Throwable {
        Signature signature = joinPoint.getSignature();

        String method = signature.getName();
        String declaringType = signature.getDeclaringTypeName();
        Object[] args = joinPoint.getArgs();

        log.atLevel(level)
                .log("Enter: {}.{}() with args = ({})", declaringType, method, argsText(args));

        try {
            Object result = joinPoint.proceed();
            log.atLevel(level)
                    .log("Exit: {}.{}() with result = {}", declaringType, method, result);
            return result;

        } catch (IllegalArgumentException ex) {
            log.error("Wrong argument: {} в {}.{}()", argsText(args), declaringType, method);
            throw ex;
        }
    }

    private String argsText(Object[] args) {
        StringJoiner joiner = new StringJoiner(", ");
        for (Object arg : args) {
            joiner.add(arg == null ? "null" : arg.toString());
        }
        return joiner.toString();
    }
}
