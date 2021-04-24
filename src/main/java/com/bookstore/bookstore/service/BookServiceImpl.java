package com.bookstore.bookstore.service;

import com.bookstore.bookstore.exception.BookStoreException;
import com.bookstore.bookstore.model.Book;
import com.bookstore.bookstore.model.Checkout;
import com.bookstore.bookstore.model.PromoCode;
import com.bookstore.bookstore.model.PromoCodeDetails;
import com.bookstore.bookstore.repository.BookRepository;
import com.bookstore.bookstore.repository.PromoCodesRepository;
import com.bookstore.bookstore.utils.BookTypeEnum;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private PromoCodesRepository promoCodesRepository;

    @Override
    public String addBook(Book book) {
        bookRepository.save(book);
        return "BOOK SUCCESSFULLY ADDED";
    }

    @Override
    public String updateBook(Long id, Book book) throws BookStoreException {
        String result = "BOOK SUCCESSFULLY UPDATED";
        Book bookEntity = bookRepository.getBookById(id);
        if (bookEntity != null) {
            bookEntity.setAuthor(book.getAuthor());
            bookEntity.setDescription(book.getDescription());
            bookEntity.setIsbn(book.getIsbn());
            bookEntity.setName(book.getName());
            bookEntity.setPrice(book.getPrice());
            //bookEntity.setPromoCode(book.getPromoCode());
            bookEntity.setType(book.getType());
            bookRepository.save(bookEntity);
        } else {
            throw new BookStoreException("Book Id is not valid");
        }
        return result;
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book getBookById(Long id) {
        return bookRepository.getBookById(id);
    }

    @Override
    public String deleteBook(List<Long> ids) throws BookStoreException {
        String result = "BOOK SUCCESSFULLY DELETED";
        if (ids.size() > 0) {
            List<Book> books = bookRepository.findAllById(ids);
            bookRepository.deleteAll(books);
        } else {
            throw new BookStoreException("Book Ids are not valid");
        }
        return result;
    }

    @Override
    public Long getCheckoutPrice(Checkout checkout) {
        Long totalAmount = 0l;
        if (checkout.getIds().size() > 0) {
            Map<String, String> resultMap = null;
            if (checkout.getPromoCode() != null && checkout.getPromoCode().length() > 0) {
                List<PromoCode> applicableDiscounts =
                        promoCodesRepository.findByPromocode(checkout.getPromoCode());
                if (CollectionUtils.isNotEmpty(applicableDiscounts)) {
                    resultMap =
                            applicableDiscounts.get(0).getPromoCodeDetailsList().stream().collect(Collectors.toMap(
                                    PromoCodeDetails::getType, PromoCodeDetails::getDiscountpct));
                    System.out.println(resultMap);
                }
            }

            List<Book> checkoutBooks = bookRepository.findAllById(checkout.getIds());
            Map<BookTypeEnum, List<Book>> groupBookByType =
                    checkoutBooks.stream().collect(Collectors.groupingBy(book -> book.getType()));

            Iterator<Map.Entry<BookTypeEnum, List<Book>>> iterator = groupBookByType.entrySet().iterator();

            while (iterator.hasNext()) {
                Map.Entry<BookTypeEnum, List<Book>> entry = iterator.next();
                long sum = entry.getValue().stream().mapToLong(book -> book.getPrice()).sum();
                if (MapUtils.isNotEmpty(resultMap) && resultMap.containsKey(entry.getKey().name())) {
                    String applicablePct = resultMap.get(entry.getKey().name());
                    int ratePct = 100 - Integer.valueOf(applicablePct);
                    long finalAmt = (ratePct * sum) / 100;
                    System.out.println(applicablePct + "---" + sum + "---" + finalAmt);
                    totalAmount += finalAmt;
                } else {
                    totalAmount += sum;
                }
            }
        }
        return totalAmount;
    }
}
