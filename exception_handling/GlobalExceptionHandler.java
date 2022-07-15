package com.khmal.hospital.exception_handling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<IncorrectData> handleGetUserException(NoSuchUserException noSuchUserException){
        IncorrectData data = new IncorrectData();
        data.setInfo(noSuchUserException.getMessage());

        return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
    }


    //parameter draft, only for instance
    @ExceptionHandler
    public ResponseEntity<IncorrectData> handleSaveException(NoSuchUserException noSuchUserException){
        IncorrectData data = new IncorrectData();
        data.setInfo(noSuchUserException.getMessage());

        // HttpStatus draft
        return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
    }
}
