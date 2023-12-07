package ru.t1consulting.charactercounter.exception;

public class InputStringIsTooLongException extends BadRequestException {
    public InputStringIsTooLongException(String message) {
        super(message);
    }
}
