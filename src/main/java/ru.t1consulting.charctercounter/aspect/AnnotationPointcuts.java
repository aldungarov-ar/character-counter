package ru.t1consulting.charctercounter.aspect;

import org.aspectj.lang.annotation.Pointcut;

@SuppressWarnings("unused")
public class AnnotationPointcuts {
    @Pointcut("(@target(ru.t1consulting.charctercounter.annotation.Info)) && " +
            "!@annotation(ru.t1consulting.charctercounter.annotation.NotLoggable)")
    public void info() {
    }

    @Pointcut("(@target(ru.t1consulting.charctercounter.annotation.Debug)) &&" +
            "!@annotation(ru.t1consulting.charctercounter.annotation.NotLoggable)")
    public void debug() {
    }
}
