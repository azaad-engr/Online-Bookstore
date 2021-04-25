package com.online.bookstore.model;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@ToString
@Data
public class Checkout {

    @NotEmpty
    private List<Long> ids;
    private String promoCode;
}
