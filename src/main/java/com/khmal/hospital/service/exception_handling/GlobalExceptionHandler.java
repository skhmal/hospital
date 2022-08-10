package com.khmal.hospital.service.exception_handling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public String handleGetUserException(NoSuchUserException noSuchUserException){
        IncorrectData data = new IncorrectData();
        data.setInfo(noSuchUserException.getMessage());

        return "error";
    }

    @ExceptionHandler
    public String handleSaveException(IncorrectDateException exception){
        IncorrectData data = new IncorrectData();
        data.setInfo(exception.getMessage());

        return "error";
    }

    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleConstraintValidationException(ConstraintViolationException exception){
        final List<Violation> errorResponseList = exception.getConstraintViolations()
                .stream()
                .map(violation -> new Violation(
                        violation.getPropertyPath().toString(),
                        violation.getMessage()
                )).collect(Collectors.toList());

        return "error";
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleMethodArgumentNotValidException(MethodArgumentNotValidException  exception){
        final List<Violation> errorResponseList = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(violation -> new Violation(
                        violation.getField(),
                        violation.getDefaultMessage()
                )).collect(Collectors.toList());

        return "error";
    }
}
