package ru.t1consulting.charactercounter.exception;

public class EmptyInputStringException extends BadRequestException {
    public EmptyInputStringException(String message) {
        super(message);
    }
}
