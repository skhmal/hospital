package com.khmal.hospital.controller.exception.handling;

public class IncorrectDataException extends RuntimeException{
    public IncorrectDataException(String message) {
        super(message);
    }
}
