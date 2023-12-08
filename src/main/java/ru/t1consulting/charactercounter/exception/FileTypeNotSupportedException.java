package ru.t1consulting.charactercounter.exception;

public class FileTypeNotSupportedException extends BadRequestException {
    public FileTypeNotSupportedException(String message) {
        super(message);
    }
}
