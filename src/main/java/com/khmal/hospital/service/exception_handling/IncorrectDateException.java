package com.khmal.hospital.service.exception_handling;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class IncorrectDateException extends RuntimeException{
    public IncorrectDateException(String message) {
        super(message);
    }
}
