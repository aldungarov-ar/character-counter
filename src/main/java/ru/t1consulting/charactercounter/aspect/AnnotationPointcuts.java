package ru.t1consulting.charactercounter.aspect;

import org.aspectj.lang.annotation.Pointcut;

@SuppressWarnings("unused")
public class AnnotationPointcuts {
    @Pointcut("(@target(ru.t1consulting.charactercounter.annotation.Info)) && " +
            "!@annotation(ru.t1consulting.charactercounter.annotation.NotLoggable)")
    public void info() {
    }

    @Pointcut("(@target(ru.t1consulting.charactercounter.annotation.Debug)) &&" +
            "!@annotation(ru.t1consulting.charactercounter.annotation.NotLoggable)")
    public void debug() {
    }
}
