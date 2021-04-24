package com.online.bookstore.exception;

public class BookNotFoundException extends RuntimeException{

    public BookNotFoundException(Long id) {
        super(String.format("Book with Id %d not found", id));
    }
}
