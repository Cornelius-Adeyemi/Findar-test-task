package com.findar.bookstore.dto.authenitcationDTO;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthenticationRequest {

    @NotNull(message = "email field is required")
    @NotEmpty(message = "email field is required")
    private String email;

    @NotNull(message = "password field is required")
    @NotEmpty(message = "password field is required")
    private String password;
}
