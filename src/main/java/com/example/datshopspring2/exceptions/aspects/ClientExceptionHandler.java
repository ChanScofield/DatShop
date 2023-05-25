package com.example.datshopspring2.exceptions.aspects;

import com.example.datshopspring2.exceptions.BookNotFoundException;
import freemarker.core.InvalidReferenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
@ControllerAdvice
public class ClientExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(ClientExceptionHandler.class);

    @ExceptionHandler(NullPointerException.class)
    public String handleNullPointerException(NullPointerException e) {
        logger.error("NullPointerException!", e);
        return "/views/error";
    }

//    @ExceptionHandler(Exception.class)
//    public String handleAnotherException(Exception e) {
//        logger.error("An unknown error!", e);
//        return "/views/error";
//    }

    @ExceptionHandler(BookNotFoundException.class)
    public String handleAnotherException(BookNotFoundException e) {
        logger.error("Book Not Found", e);
        return "/views/error";
    }

    @ExceptionHandler(InvalidReferenceException.class)
    public String handleInvalidReferenceException(InvalidReferenceException e) {
        logger.error("An unknown error!", e);
        return "/views/error";
    }

}
