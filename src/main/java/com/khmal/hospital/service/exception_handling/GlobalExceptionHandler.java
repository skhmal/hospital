package com.khmal.hospital.service.exception_handling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final String EXCEPTION = "exception";
    private static final String ERROR_VIEW_NAME = "error";

    @ExceptionHandler
    public ModelAndView handleGetUserException(NoSuchUserException noSuchUserException){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject(EXCEPTION, noSuchUserException.getMessage());

        modelAndView.setStatus(HttpStatus.BAD_REQUEST);
        modelAndView.setViewName(ERROR_VIEW_NAME);

        return modelAndView;
    }

    @ExceptionHandler
    public ModelAndView handleSaveException(IncorrectDateException exception){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject(EXCEPTION, exception.getMessage());

        modelAndView.setStatus(HttpStatus.BAD_REQUEST);
        modelAndView.setViewName(ERROR_VIEW_NAME);

        return modelAndView;
    }
    @ExceptionHandler(ConstraintViolationException.class)
    public ModelAndView handleEmployeeNotFoundException(HttpServletRequest request, Exception ex){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject(EXCEPTION, ex.getLocalizedMessage());
        modelAndView.addObject("url", request);

        modelAndView.setStatus(HttpStatus.BAD_REQUEST);
        modelAndView.setViewName(ERROR_VIEW_NAME);
        return modelAndView;
    }



    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ModelAndView handleMethodArgumentNotValidException(MethodArgumentNotValidException  exception){

        ModelAndView error = new ModelAndView();
        error.addObject(EXCEPTION, exception.getMessage());

        error.setStatus(HttpStatus.BAD_REQUEST);
        error.setViewName(ERROR_VIEW_NAME);

        return error;
    }
}
