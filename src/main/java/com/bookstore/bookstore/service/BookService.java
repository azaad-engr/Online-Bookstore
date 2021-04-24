package com.bookstore.bookstore.service;

import com.bookstore.bookstore.exception.BookStoreException;
import com.bookstore.bookstore.model.Book;
import com.bookstore.bookstore.model.Checkout;

import java.util.List;

public interface BookService {
    String addBook(Book book);

    String updateBook(Long id, Book book) throws BookStoreException;

    List<Book> getAllBooks();

    Book getBookById(Long id);

    String deleteBook(List<Long> ids) throws BookStoreException;

    Long getCheckoutPrice(Checkout checkout);
}
