package com.bookstore.bookstore.resource;

import com.bookstore.bookstore.model.Book;
import com.bookstore.bookstore.model.Checkout;
import com.bookstore.bookstore.service.BookService;
import com.bookstore.bookstore.exception.BookStoreException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping(value = "/addBook", produces = {MediaType.TEXT_PLAIN_VALUE})
    public String addBook(@RequestBody Book book) {
        return bookService.addBook(book);
    }

    @GetMapping(value = "/getBookByid/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Book> getBookByid(@PathVariable("id") Long id) {
        return new ResponseEntity<>(
                bookService.getBookById(id), bookService.getBookById(id) != null ? HttpStatus.OK : HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/getAllBooks", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @PutMapping(value = "/updateBookById/{id}", produces = {MediaType.TEXT_PLAIN_VALUE})
    public String updateBook(@PathVariable("id") Long id, @RequestBody Book book) throws BookStoreException {
        return bookService.updateBook(id, book);
    }

    @DeleteMapping(value = "/deleteBookById", produces = {MediaType.TEXT_PLAIN_VALUE})
    public String updateBook(@RequestBody List<Long> ids) throws BookStoreException {
        return bookService.deleteBook(ids);
    }


    @GetMapping(value = "/checkout", produces = {MediaType.TEXT_PLAIN_VALUE})
    public String checkout(@RequestBody Checkout checkoutBook) {
        Long totalAmount = bookService.getCheckoutPrice(checkoutBook);
        return "BOOKS CHECKED OUT - Total Payable is AED" + totalAmount;
    }
}
