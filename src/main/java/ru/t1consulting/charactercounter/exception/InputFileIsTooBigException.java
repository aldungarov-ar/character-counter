package ru.t1consulting.charactercounter.exception;

public class InputFileIsTooBigException extends BadRequestException {
    public InputFileIsTooBigException(String message) {
        super(message);
    }
}
