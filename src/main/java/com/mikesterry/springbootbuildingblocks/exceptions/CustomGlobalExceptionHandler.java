package com.mikesterry.springbootbuildingblocks.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        CustomErrorDetails customErrorDetails = new CustomErrorDetails(new Date(), "From MethodArgumentNotValid Exception in GEH",
                ex.getMessage());
        return new ResponseEntity<>(customErrorDetails, HttpStatus.BAD_REQUEST);
//        return super.handleMethodArgumentNotValid( ex, headers, status, request );
    }
}
