package com.online.bookstore.resource;

import com.online.bookstore.model.Book;
import com.online.bookstore.model.BookStoreResponse;
import com.online.bookstore.model.Checkout;
import com.online.bookstore.service.BookService;
import com.online.bookstore.exception.BookStoreException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "This API is to add the book to the Online Book Store")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "When adding book is successful",
                    content = {@Content(mediaType = "application/json")}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "When any of the object data is empty or null",
                    content = {@Content(mediaType = "application/json")}
            )
    })
    @PostMapping(value = "/addBook", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<BookStoreResponse> addBook(@Valid @RequestBody Book book) {
        BookStoreResponse bookStoreResponse = new BookStoreResponse();
        bookStoreResponse.setResponse(bookService.addBook(book));
        return new ResponseEntity<>(bookStoreResponse, HttpStatus.OK);
    }

    @Operation(summary = "This API is to get the book from the Online Book Store using book id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "When the book is successfully fetched from DB",
                    content = {@Content(mediaType = "application/json")}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "When the supplied id is invalid, and no Book found in DB",
                    content = {@Content(mediaType = "application/json")}
            )
    })
    @GetMapping(value = "/getBookByid/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<BookStoreResponse> getBookByid(@PathVariable("id") Long id) {
        BookStoreResponse bookStoreResponse = new BookStoreResponse();
        bookStoreResponse.setResponse(bookService.getBookById(id));
        return new ResponseEntity<>(bookStoreResponse, HttpStatus.OK);
    }

    @Operation(summary = "This API is to get all the books in DB")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "When the books successfully fetched from DB",
                    content = {@Content(mediaType = "application/json")}
            )
    })
    @GetMapping(value = "/getAllBooks", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<BookStoreResponse> getAllBooks() {
        BookStoreResponse bookStoreResponse = new BookStoreResponse();
        bookStoreResponse.setResponse(bookService.getAllBooks());
        return new ResponseEntity<>(bookStoreResponse, HttpStatus.OK);
    }

    @Operation(summary = "This API is to update the Book for the given id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "When the books successfully updated in DB",
                    content = {@Content(mediaType = "application/json")}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "When the supplied id is invalid, and no Book found in DB",
                    content = {@Content(mediaType = "application/json")}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "When any of the object data is empty or null",
                    content = {@Content(mediaType = "application/json")}
            )
    })
    @PutMapping(value = "/updateBookById/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<BookStoreResponse> updateBook(@PathVariable("id") Long id,
                                                        @Valid @RequestBody Book book) throws BookStoreException {
        BookStoreResponse bookStoreResponse = new BookStoreResponse();
        bookStoreResponse.setResponse(bookService.updateBook(id, book));
        return new ResponseEntity<>(bookStoreResponse, HttpStatus.OK);
    }

    @Operation(summary = "This API is to delete the books from DB for the given ids")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "When the books successfully deleted in DB",
                    content = {@Content(mediaType = "application/json")}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "When the supplied ids are invalid, and no Book found in DB",
                    content = {@Content(mediaType = "application/json")}
            )
    })
    @DeleteMapping(value = "/deleteBookById", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<BookStoreResponse> deleteBooks(@RequestBody List<Long> ids) {
        BookStoreResponse bookStoreResponse = new BookStoreResponse();
        bookStoreResponse.setResponse(bookService.deleteBook(ids));
        return new ResponseEntity<>(bookStoreResponse, HttpStatus.OK);
    }

    @Operation(summary = "This API is to get book price quote after applying " +
            "discounts if any based on the promo code supplied")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "When the total payable is calculated",
                    content = {@Content(mediaType = "application/json")}
            )
    })
    @GetMapping(value = "/checkout", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<BookStoreResponse> checkout(@Valid @RequestBody Checkout checkoutBook) {
        Long totalAmount = bookService.getCheckoutPrice(checkoutBook);
        BookStoreResponse bookStoreResponse = new BookStoreResponse();
        bookStoreResponse.setResponse("BOOKS CHECKED OUT - Total Payable is AED " + totalAmount);
        return new ResponseEntity<>(bookStoreResponse, HttpStatus.OK);
    }
}
