package com.online.bookstore.service;

import com.online.bookstore.exception.BookNotFoundException;
import com.online.bookstore.exception.BookStoreException;
import com.online.bookstore.model.Book;
import com.online.bookstore.model.Checkout;
import com.online.bookstore.model.PromoCode;
import com.online.bookstore.model.PromoCodeDetails;
import com.online.bookstore.repository.BookRepository;
import com.online.bookstore.repository.PromoCodesRepository;
import com.online.bookstore.utils.BookTypeEnum;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);

    @Override
    public String addBook(Book book) throws BookStoreException{
        bookRepository.save(book);
        logger.info("Book Successfully Added");
        return "BOOK SUCCESSFULLY ADDED";
    }

    @Override
    public String updateBook(Long id, Book book) throws BookStoreException {
        String result = "BOOK SUCCESSFULLY UPDATED";
        Book bookEntity = bookRepository.findById(id).orElseThrow(()->new BookNotFoundException(id));
        logger.info("Fetched Book {} with id {} ", bookEntity!=null?bookEntity.toString():null, id);
        if (bookEntity != null) {
            bookEntity.setAuthor(book.getAuthor());
            bookEntity.setDescription(book.getDescription());
            bookEntity.setIsbn(book.getIsbn());
            bookEntity.setName(book.getName());
            bookEntity.setPrice(book.getPrice());
            bookEntity.setType(book.getType());
            bookRepository.save(bookEntity);
        } else {
            throw new BookStoreException("Book Id is not valid");
        }
        return result;
    }

    @Override
    public List<Book> getAllBooks() {
        return (List<Book>) bookRepository.findAll();
    }

    @Override
    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElseThrow(()->new BookNotFoundException(id));
    }

    @Override
    public String deleteBook(List<Long> ids) throws BookStoreException {
        String result = "BOOK SUCCESSFULLY DELETED";
        if (ids.size() > 0) {
            List<Book> books = (List<Book>) bookRepository.findAllById(ids);
            logger.info("No of books fetched with ids {} {}", ids, books!=null?books.size():0);
            if(CollectionUtils.isNotEmpty(books)){
                bookRepository.deleteAll(books);
            }else{
                throw new BookStoreException("Book Ids are not valid");
            }
        } else {
            throw new BookStoreException("Book Ids are not valid");
        }
        return result;
    }

    @Override
    public Long getCheckoutPrice(Checkout checkout) throws BookStoreException{
        Long totalAmount = 0l;
        if (checkout.getIds().size() > 0) {
            Map<String, String> resultMap = null;
            if (checkout.getPromoCode() != null && checkout.getPromoCode().length() > 0) {
                List<PromoCode> applicableDiscounts =
                        promoCodesRepository.findByPromocode(checkout.getPromoCode());
                logger.info("Applicable Promo Codes {} with Name {}",
                        applicableDiscounts!=null?applicableDiscounts.size():0, checkout.getPromoCode());
                if (CollectionUtils.isNotEmpty(applicableDiscounts)) {
                    resultMap =
                            applicableDiscounts.get(0).getPromoCodeDetailsList().stream().collect(Collectors.toMap(
                                    PromoCodeDetails::getType, PromoCodeDetails::getDiscountpct));
                }
            }

            List<Book> checkoutBooks = (List<Book>) bookRepository.findAllById(checkout.getIds());
            logger.info("Books {} Checkout with ids {}",
                    checkoutBooks!=null?checkoutBooks.size():0, checkout.getIds());
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
                    totalAmount += finalAmt;
                } else {
                    totalAmount += sum;
                }
            }
        }
        return totalAmount;
    }
}
