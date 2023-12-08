package ru.t1consulting.charactercounter.aspect;

import org.aspectj.lang.annotation.Pointcut;

@SuppressWarnings("unused")
public class LoggingPointcuts {
    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *) || " +
            "within(@org.springframework.stereotype.Component *) || " +
            "within(@org.springframework.stereotype.Service *)")
    public void springBeanPointcut() {
    }

    @Pointcut("within(ru.t1consulting.charactercounter..*)")
    public void applicationPackagePointcut() {
    }
}
