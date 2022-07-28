package com.khmal.hospital.service.exception_handling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<IncorrectData> handleGetUserException(NoSuchUserException noSuchUserException){
        IncorrectData data = new IncorrectData();
        data.setInfo(noSuchUserException.getMessage());

        return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<IncorrectData> handleSaveException(IncorrectDateException exception){
        IncorrectData data = new IncorrectData();
        data.setInfo(exception.getMessage());

        return new ResponseEntity<>(data, HttpStatus.PRECONDITION_FAILED);
    }

    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorResponse handleConstraintValidationException(ConstraintViolationException exception){
        final List<Violation> errorResponseList = exception.getConstraintViolations()
                .stream()
                .map(violation -> new Violation(
                        violation.getPropertyPath().toString(),
                        violation.getMessage()
                )).collect(Collectors.toList());
        return new ValidationErrorResponse(errorResponseList);
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException  exception){
        final List<Violation> errorResponseList = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(violation -> new Violation(
                        violation.getField(),
                        violation.getDefaultMessage()
                )).collect(Collectors.toList());
        return new ValidationErrorResponse(errorResponseList);
    }
}
