package com.khmal.hospital.service.exception_handling;

public class IncorrectDateException extends RuntimeException{
    public IncorrectDateException(String message) {
        super(message);
    }
}
