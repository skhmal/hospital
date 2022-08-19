package com.khmal.hospital.controller.exception.handling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

/**
 * Exceptions handling
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private static final String EXCEPTION = "exception";
    private static final String ERROR_VIEW_NAME = "error";

    /**
     * User not found exceptions
     * @param noSuchUserException
     * @return
     */
    @ExceptionHandler
    public ModelAndView handleGetUserException(NoSuchUserException noSuchUserException){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject(EXCEPTION, noSuchUserException.getMessage());

        modelAndView.setStatus(HttpStatus.BAD_REQUEST);
        modelAndView.setViewName(ERROR_VIEW_NAME);

        logger.error("NoSuchUserException - {}", noSuchUserException.getMessage());

        return modelAndView;
    }

    /**
     * Incorrect data exceptions
     * @param exception
     * @return modelAndView
     */
    @ExceptionHandler
    public ModelAndView handleSaveException(IncorrectDataException exception){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject(EXCEPTION, exception.getMessage());

        modelAndView.setStatus(HttpStatus.BAD_REQUEST);
        modelAndView.setViewName(ERROR_VIEW_NAME);

        logger.error("NoSuchUserException - {}", exception.getMessage());

        return modelAndView;
    }

    /**
     * Validation exceptions
     * @param request
     * @param ex
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ModelAndView handleEmployeeNotFoundException(HttpServletRequest request, Exception ex){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject(EXCEPTION, ex.getLocalizedMessage());
        modelAndView.addObject("url", request);

        modelAndView.setStatus(HttpStatus.BAD_REQUEST);
        modelAndView.setViewName(ERROR_VIEW_NAME);

        return modelAndView;
    }


    /**
     * Validation exceptions
     * @param exception
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ModelAndView handleMethodArgumentNotValidException(MethodArgumentNotValidException  exception){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject(EXCEPTION, exception.getMessage());

        modelAndView.setStatus(HttpStatus.BAD_REQUEST);
        modelAndView.setViewName(ERROR_VIEW_NAME);

        return modelAndView;
    }
}
