package com.online.bookstore.resource;

import com.online.bookstore.model.Book;
import com.online.bookstore.model.BookStoreResponse;
import com.online.bookstore.model.Checkout;
import com.online.bookstore.service.BookService;
import com.online.bookstore.exception.BookStoreException;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping(value = "/addBook", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<BookStoreResponse> addBook(@Valid @RequestBody Book book) {
        BookStoreResponse bookStoreResponse = new BookStoreResponse();
        bookStoreResponse.setResponse(bookService.addBook(book));
        return new ResponseEntity<>(bookStoreResponse, HttpStatus.OK);
    }

    @GetMapping(value = "/getBookByid/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<BookStoreResponse> getBookByid(@PathVariable("id") Long id) {
        BookStoreResponse bookStoreResponse = new BookStoreResponse();
        bookStoreResponse.setResponse(bookService.getBookById(id));
        return new ResponseEntity<>(bookStoreResponse, HttpStatus.OK);
    }

    @GetMapping(value = "/getAllBooks", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<BookStoreResponse> getAllBooks() {
        BookStoreResponse bookStoreResponse = new BookStoreResponse();
        bookStoreResponse.setResponse(bookService.getAllBooks());
        return new ResponseEntity<>(bookStoreResponse, HttpStatus.OK);
    }

    @PutMapping(value = "/updateBookById/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<BookStoreResponse> updateBook(@PathVariable("id") Long id, @RequestBody Book book) throws BookStoreException {
        BookStoreResponse bookStoreResponse = new BookStoreResponse();
        bookStoreResponse.setResponse(bookService.updateBook(id, book));
        return new ResponseEntity<>(bookStoreResponse, HttpStatus.OK);
    }

    @DeleteMapping(value = "/deleteBookById", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<BookStoreResponse> updateBook(@RequestBody List<Long> ids) {
        BookStoreResponse bookStoreResponse = new BookStoreResponse();
        bookStoreResponse.setResponse(bookService.deleteBook(ids));
        return new ResponseEntity<>(bookStoreResponse, HttpStatus.OK);
    }

    @GetMapping(value = "/checkout", produces = {MediaType.TEXT_PLAIN_VALUE})
    public String checkout(@RequestBody Checkout checkoutBook) {
        Long totalAmount = bookService.getCheckoutPrice(checkoutBook);
        return "BOOKS CHECKED OUT - Total Payable is AED" + totalAmount;
    }
}
