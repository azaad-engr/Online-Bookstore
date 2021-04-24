package com.bookstore.bookstore.model;

import com.bookstore.bookstore.utils.BookTypeEnum;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "BOOK")
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    @Column(name = "name")
    String name;
    @Column(name = "description")
    String description;
    @Column(name = "author")
    String author;
    @Column(name = "type")
    BookTypeEnum type;
    @Column(name = "price")
    Long price;
    @Column(name = "isbn")
    String isbn;
}
