package com.online.bookstore.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Error {
    private String errorCode;
    private String errorDesc;
    private List<String> errors;
}
