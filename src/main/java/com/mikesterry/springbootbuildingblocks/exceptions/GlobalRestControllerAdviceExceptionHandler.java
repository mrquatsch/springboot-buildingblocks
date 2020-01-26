package com.mikesterry.springbootbuildingblocks.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Date;

//@RestControllerAdvice
public class GlobalRestControllerAdviceExceptionHandler {
    @ExceptionHandler(UserNameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CustomErrorDetails usernameNotFound(UserNameNotFoundException ex) {
        return new CustomErrorDetails(new Date(), "From @RestControllerAdvice - NotFound", ex.getMessage());
    }
}
