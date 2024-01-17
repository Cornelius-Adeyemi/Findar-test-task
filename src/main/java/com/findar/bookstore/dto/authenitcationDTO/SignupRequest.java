package com.findar.bookstore.dto.authenitcationDTO;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
public class SignupRequest {


    @NotNull(message = "firstname field is required")
    @NotEmpty(message = "firstname field is required")
    private String firstname;

    @NotNull(message = "lastname field is required")
    @NotEmpty(message = "lastname field is required")
    private String lastname;


    @NotNull(message = "email field is required")
    @NotEmpty(message = "email field is required")
    private String email;

    @NotNull(message = "password field is required")
    @NotEmpty(message = "password field is required")
    private String password;

    private String adminPassCode;



}
