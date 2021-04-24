package com.online.bookstore.model;

import com.online.bookstore.utils.BookTypeEnum;
import lombok.Data;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "BOOK")
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @NotBlank
    @Column(name = "name")
    String name;

    @NotBlank
    @Column(name = "description")
    String description;

    @NotBlank
    @Column(name = "author")
    String author;

    @Column(name = "type")
    BookTypeEnum type;

    @NotNull
    @Column(name = "price")
    Long price;

    @NotBlank
    @Column(name = "isbn")
    String isbn;
}
