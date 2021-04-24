package com.bookstore.bookstore.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "PROMOCODE")
@Data
public class PromoCode {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column(name = "promocode")
    String promocode;

    @OneToMany(targetEntity = PromoCodeDetails.class, mappedBy = "promocodeid", orphanRemoval = false, fetch = FetchType.LAZY)
    List<PromoCodeDetails> promoCodeDetailsList;
}
