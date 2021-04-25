package com.online.bookstore;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.online.bookstore.model.Book;
import com.online.bookstore.model.Checkout;
import com.online.bookstore.resource.BookController;
import com.online.bookstore.service.BookService;
import com.online.bookstore.utils.BookTypeEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = BookController.class)
public class BookControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    BookService bookService;

    @Test
    public void addBook() throws Exception {

        //Given
        Book mockBook = new Book();
        mockBook.setName("abc");
        mockBook.setDescription("def");
        mockBook.setAuthor("ghi");
        mockBook.setIsbn("jkl");
        mockBook.setType(BookTypeEnum.COMIC);
        mockBook.setPrice(120l);

        //Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/addBook")
                .content(new ObjectMapper().writeValueAsString(mockBook))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void getBookByid() throws Exception {

        //Given
        Book mockBook = new Book();
        mockBook.setName("abc");
        mockBook.setDescription("def");
        mockBook.setAuthor("ghi");
        mockBook.setIsbn("jkl");
        mockBook.setType(BookTypeEnum.COMIC);
        mockBook.setPrice(120l);
        mockBook.setId(1l);

        //When
        doReturn(mockBook).when(bookService).getBookById(mockBook.getId());

        //Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/getBookByid/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.response.name", is("abc")));
    }

    @Test
    public void getAllBooks() throws Exception {

        //Given
        Book mockBook_1 = new Book();
        mockBook_1.setName("abc");
        mockBook_1.setDescription("def");
        mockBook_1.setAuthor("ghi");
        mockBook_1.setIsbn("jkl");
        mockBook_1.setType(BookTypeEnum.COMIC);
        mockBook_1.setPrice(120l);
        mockBook_1.setId(1l);

        Book mockBook_2 = new Book();
        mockBook_2.setName("mno");
        mockBook_2.setDescription("pqr");
        mockBook_2.setAuthor("stu");
        mockBook_2.setIsbn("vwx");
        mockBook_2.setType(BookTypeEnum.FICTION);
        mockBook_2.setPrice(150l);
        mockBook_2.setId(2l);

        List<Book> books = new ArrayList<>();
        books.add(mockBook_1);
        books.add(mockBook_2);

        //When
        doReturn(books).when(bookService).getAllBooks();

        //Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/getAllBooks"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.response.[0].name", is("abc")))
                .andExpect(jsonPath("$.response.[1].name", is("mno")));
    }

    @Test
    public void updateBook() throws Exception {

        //Given
        Book mockBook = new Book();
        mockBook.setName("abc");
        mockBook.setDescription("def");
        mockBook.setAuthor("ghi");
        mockBook.setIsbn("jkl");
        mockBook.setType(BookTypeEnum.COMIC);
        mockBook.setPrice(120l);
        mockBook.setId(1l);

        //When
        doReturn("BOOK SUCCESSFULLY UPDATED").when(bookService).updateBook(mockBook.getId(), mockBook);

        //Then
        mockMvc.perform(put("/api/updateBookById/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(new ObjectMapper().writeValueAsString(mockBook)))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.response", is("BOOK SUCCESSFULLY UPDATED")));
    }

    @Test
    public void deleteBooks() throws Exception {
        //Given
        Book mockBook = new Book();
        mockBook.setName("abc");
        mockBook.setDescription("def");
        mockBook.setAuthor("ghi");
        mockBook.setIsbn("jkl");
        mockBook.setType(BookTypeEnum.COMIC);
        mockBook.setPrice(120l);
        mockBook.setId(1l);

        //When
        doReturn("BOOK SUCCESSFULLY DELETED").when(bookService).deleteBook(Arrays.asList(mockBook.getId()));

        //Then
        mockMvc.perform(delete("/api/deleteBookById")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(new ObjectMapper().writeValueAsString(Arrays.asList(mockBook.getId()))))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.response", is("BOOK SUCCESSFULLY DELETED")));
    }

    @Test
    public void checkout() throws Exception {
        //Given
        Checkout checkout = new Checkout();
        checkout.setIds(Arrays.asList(1l));
        checkout.setPromoCode("FLASH-10");

        //When
        doReturn(120l).when(bookService).getCheckoutPrice(checkout);

        //Then
        mockMvc.perform(get("/api/checkout")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(new ObjectMapper().writeValueAsString(checkout)))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.response", is("BOOKS CHECKED OUT - Total Payable is AED 120")));
    }
}