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
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Arrays;

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
     *
     * @param noSuchUserException
     * @return
     */
    @ExceptionHandler
    public ModelAndView handleGetUserException(NoSuchUserException noSuchUserException) {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject(EXCEPTION, noSuchUserException.getMessage());

        modelAndView.setStatus(HttpStatus.BAD_REQUEST);
        modelAndView.setViewName(ERROR_VIEW_NAME);

        logger.error("NoSuchUserException - {}", noSuchUserException.getMessage());

        return modelAndView;
    }

    /**
     * Incorrect data exceptions
     *
     * @param exception
     * @return modelAndView
     */
    @ExceptionHandler
    public ModelAndView handleSaveException(IncorrectDataException exception) {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject(EXCEPTION, exception.getMessage());

        modelAndView.setStatus(HttpStatus.BAD_REQUEST);
        modelAndView.setViewName(ERROR_VIEW_NAME);

        logger.error("NoSuchUserException - {}", exception.getMessage());

        return modelAndView;
    }

    /**
     * Validation exceptions
     *
     * @param request
     * @param ex
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ModelAndView handleEmployeeNotFoundException(HttpServletRequest request, ConstraintViolationException ex) {

        ModelAndView modelAndView = new ModelAndView();
        String errorMessage = new ArrayList<>(ex.getConstraintViolations()).get(0).getMessage();

        modelAndView.addObject(EXCEPTION, errorMessage);
        modelAndView.addObject("url", request);

        modelAndView.setStatus(HttpStatus.BAD_REQUEST);
        modelAndView.setViewName(ERROR_VIEW_NAME);

        return modelAndView;
    }


    /**
     * Validation exceptions
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ModelAndView handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject(EXCEPTION, exception.getMessage());

        modelAndView.setStatus(HttpStatus.BAD_REQUEST);
        modelAndView.setViewName(ERROR_VIEW_NAME);

        return modelAndView;
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ModelAndView handleSQLException(SQLIntegrityConstraintViolationException exception) {
        ModelAndView modelAndView = new ModelAndView();
        int duplicateErrorCode = 1062;
        StringBuilder errorMessage = new StringBuilder("SQL error " + exception.getErrorCode() + "!");

        if (exception.getErrorCode() == duplicateErrorCode) {
            String duplicatedRegistrationLogin = exception.getMessage().split("'")[1];
            errorMessage.append(" User with login - \"").append(duplicatedRegistrationLogin).append("\" already exist!");
        }

        modelAndView.addObject(EXCEPTION, errorMessage);

        modelAndView.setStatus(HttpStatus.BAD_REQUEST);
        modelAndView.setViewName(ERROR_VIEW_NAME);

        return modelAndView;
    }
}
