package com.online.bookstore.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class Checkout {

    @NotEmpty
    private List<Long> ids;
    private String promoCode;
}
