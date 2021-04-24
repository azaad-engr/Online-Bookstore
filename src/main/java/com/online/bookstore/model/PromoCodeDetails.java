package com.online.bookstore.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "PROMOCODE_DETAILS")
@Data
public class PromoCodeDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column(name = "promocodeid")
    Long promocodeid;

    @Column(name = "type")
    String type;

    @Column(name = "discountpct")
    String discountpct;
}
