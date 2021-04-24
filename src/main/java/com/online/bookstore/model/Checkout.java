package com.online.bookstore.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
public class Checkout {

    private List<Long> ids;
    private String promoCode;
}
