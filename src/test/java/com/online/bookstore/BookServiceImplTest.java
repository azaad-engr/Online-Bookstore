package com.online.bookstore;

import com.online.bookstore.model.Book;
import com.online.bookstore.model.Checkout;
import com.online.bookstore.model.PromoCode;
import com.online.bookstore.model.PromoCodeDetails;
import com.online.bookstore.repository.BookRepository;
import com.online.bookstore.repository.PromoCodesRepository;
import com.online.bookstore.service.BookService;
import com.online.bookstore.utils.BookTypeEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.AssertionErrors;

import java.util.*;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class BookServiceImplTest {

    @Autowired
    private BookService bookService;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private PromoCodesRepository promoCodesRepository;

    @Test
    void addBook() {
        //Given
        Book mockBook = new Book();
        mockBook.setName("abc");
        mockBook.setDescription("def");
        mockBook.setAuthor("ghi");
        mockBook.setIsbn("jkl");
        mockBook.setType(BookTypeEnum.COMIC);
        mockBook.setPrice(120l);

        //When
        doReturn(mockBook).when(bookRepository).save(any());

        //Then
        String book = bookService.addBook(mockBook);

        AssertionErrors.assertNotNull("Book should not be null", book);
        assertEquals("BOOK SUCCESSFULLY ADDED", book);
    }

    @Test
    void updateBook() {
        //Given
        Book existingBook = new Book();
        existingBook.setName("abc");
        existingBook.setDescription("def");
        existingBook.setAuthor("ghi");
        existingBook.setIsbn("jkl");
        existingBook.setType(BookTypeEnum.COMIC);
        existingBook.setPrice(120l);
        existingBook.setId(1l);

        Book updatedBook = new Book();
        updatedBook.setName("xyz");
        updatedBook.setDescription("def");
        updatedBook.setAuthor("ghi");
        updatedBook.setIsbn("jkl");
        updatedBook.setType(BookTypeEnum.COMIC);
        updatedBook.setPrice(120l);
        updatedBook.setId(1l);

        //When
        doReturn(Optional.of(existingBook)).when(bookRepository).findById(1l);
        doReturn(updatedBook).when(bookRepository).save(updatedBook);

        //Then
        String updateBook = bookService.updateBook(existingBook.getId(), existingBook);

        assertEquals("BOOK SUCCESSFULLY UPDATED", updateBook);
    }

    @Test
    void getAllBooks() {
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

        //When
        doReturn(Arrays.asList(mockBook_1, mockBook_2)).when(bookRepository).findAll();

        //Then
        List<Book> allProducts = bookService.getAllBooks();

        Assertions.assertEquals(2, ((Collection<?>) allProducts).size());
    }

    @Test
    void getBookById() {
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
        doReturn(Optional.of(mockBook)).when(bookRepository).findById(1l);
        Book bookById = bookService.getBookById(1l);

        //Then
        Assertions.assertNotNull(bookById);
        Assertions.assertSame("abc", bookById.getName());

    }

    @Test
    void deleteBook() {
        //Given
        Book existingBook = new Book();
        existingBook.setName("abc");
        existingBook.setDescription("def");
        existingBook.setAuthor("ghi");
        existingBook.setIsbn("jkl");
        existingBook.setType(BookTypeEnum.COMIC);
        existingBook.setPrice(120l);
        existingBook.setId(1l);

        //When
        doReturn(Arrays.asList(existingBook)).when(bookRepository).findAllById(Arrays.asList(existingBook.getId()));
        doAnswer(i-> {
            return null;
        }).when(bookRepository).deleteAll(Arrays.asList(existingBook));

        //Then
        String deleteBook = bookService.deleteBook(Arrays.asList(existingBook.getId()));

        assertEquals("BOOK SUCCESSFULLY DELETED", deleteBook);
    }

    @Test
    void getCheckoutPrice() {
        //Given
        Checkout checkout = new Checkout();
        checkout.setIds(Arrays.asList(1l));
        checkout.setPromoCode("FLASH-10");

        List<PromoCodeDetails> promoCodeDetailsList = new ArrayList<>();
        PromoCodeDetails promoCodeDetails = new PromoCodeDetails();
        promoCodeDetails.setId(1l);
        promoCodeDetails.setType("FICTION");
        promoCodeDetails.setDiscountpct("10");
        promoCodeDetails.setPromocodeid(1l);
        promoCodeDetailsList.add(promoCodeDetails);

        List<PromoCode> applicableDiscounts = new ArrayList<>();
        PromoCode promoCode = new PromoCode();
        promoCode.setId(1l);;
        promoCode.setPromocode("FLASH-10");
        promoCode.setPromoCodeDetailsList(promoCodeDetailsList);

        Book mockBook = new Book();
        mockBook.setName("abc");
        mockBook.setDescription("def");
        mockBook.setAuthor("ghi");
        mockBook.setIsbn("jkl");
        mockBook.setType(BookTypeEnum.FICTION);
        mockBook.setPrice(120l);
        mockBook.setId(1l);

        //When
        doReturn(applicableDiscounts).when(promoCodesRepository).findByPromocode(checkout.getPromoCode());
        doReturn(Arrays.asList(mockBook)).when(bookRepository).findAllById(Arrays.asList(mockBook.getId()));

        //Then
        Long checkoutPrice = bookService.getCheckoutPrice(checkout);

        assertEquals(120l, checkoutPrice);
    }
}