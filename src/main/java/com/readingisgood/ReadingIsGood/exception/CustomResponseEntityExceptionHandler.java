package com.readingisgood.ReadingIsGood.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomResponseEntityExceptionHandler  {

    @ExceptionHandler(BookNotFoundException.class)
    public final ResponseEntity<Object> handleBookNotFoundException(BookNotFoundException e) {

        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(CustomerNotFoundException.class)
    public final ResponseEntity<Object> handleCustomerNotFoundException(CustomerNotFoundException e) {

        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
}
