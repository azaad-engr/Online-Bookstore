package com.online.bookstore.service;

import com.online.bookstore.exception.BookStoreException;
import com.online.bookstore.model.Book;
import com.online.bookstore.model.Checkout;

import java.util.List;

public interface BookService {
    String addBook(Book book);

    String updateBook(Long id, Book book) throws BookStoreException;

    List<Book> getAllBooks();

    Book getBookById(Long id);

    String deleteBook(List<Long> ids) throws BookStoreException;

    Long getCheckoutPrice(Checkout checkout);
}
