package com.online.bookstore.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookStoreResponse {
    private Object response;
    private Error error;
}
