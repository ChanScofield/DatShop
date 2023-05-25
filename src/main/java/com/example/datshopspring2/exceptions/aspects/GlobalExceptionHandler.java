package com.example.datshopspring2.exceptions.aspects;


import com.example.datshopspring2.dto.ExceptionDto;
import com.example.datshopspring2.exceptions.BookNotFoundException;
import com.example.datshopspring2.exceptions.NotFoundException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionDto> handleNotFoundException(NotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ExceptionDto.builder()
                        .status(HttpStatus.NOT_FOUND.value())
                        .message(e.getMessage())
                        .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Object[] strings = e.getDetailMessageArguments();
        assert strings != null;
        String errorMessage = strings[1].toString();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ExceptionDto.builder()
                        .message(errorMessage)
                        .status(HttpStatus.BAD_REQUEST.value())
                        .build());
    }

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ExceptionDto> handleBookNotFoundException(BookNotFoundException e) {
        return ResponseEntity
                .status(e.getCode())
                .body(ExceptionDto.builder()
                        .message(e.getMessage())
                        .status(e.getCode())
                        .build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDto> handleException(Exception e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ExceptionDto.builder()
                        .message(e.getMessage())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .build());
    }
}
