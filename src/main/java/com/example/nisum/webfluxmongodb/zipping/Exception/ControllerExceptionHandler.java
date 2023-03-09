package com.example.nisum.webfluxmongodb.zipping.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleException(ResourceNotFoundException ex) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorMessage.setErrorMessage(ex.getMessage());
        errorMessage.setErrorDescription(ex.getLocalizedMessage());
        errorMessage.setErrorDate(new Date());
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }


}
