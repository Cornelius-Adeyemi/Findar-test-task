package com.findar.bookstore.dto.bookDTO;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookRequest {

    @NotNull(message = "bookTitle field is required")
    @NotEmpty(message = "bookTitle field is required")
    private String bookTitle;

    @NotNull(message = "bookDescription field is required")
    @NotEmpty(message = "bookDescription field is required")
    private String bookDescription;


    @NotNull(message = "bookQuantity field is required")
    private Long bookQuantity;
}
