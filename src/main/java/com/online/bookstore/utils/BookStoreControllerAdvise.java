package com.online.bookstore.utils;

import com.online.bookstore.exception.BookNotFoundException;
import com.online.bookstore.exception.BookStoreException;
import com.online.bookstore.model.BookStoreResponse;
import com.online.bookstore.model.Error;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class BookStoreControllerAdvise extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<BookStoreResponse> handleBookNotFoundException(
            BookNotFoundException ex, WebRequest request) {

        BookStoreResponse response = new BookStoreResponse();
        Error error = new Error();
        error.setErrorCode("BOOK_NOT_FOUND");
        error.setErrorDesc(ex.getMessage());
        response.setError(error);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BookStoreException.class)
    public ResponseEntity<BookStoreResponse> handleBookStoreException(
            BookStoreException ex, WebRequest request) {

        BookStoreResponse response = new BookStoreResponse();
        Error error = new Error();
        error.setErrorCode("ERROR PROCESSING REQUEST");
        error.setErrorDesc(ex.getMessage());
        response.setError(error);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> (x.getField()+" "+x.getDefaultMessage()))
                .collect(Collectors.toList());

        BookStoreResponse response = new BookStoreResponse();
        Error error = new Error();
        error.setErrors(errors);
        response.setError(error);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
