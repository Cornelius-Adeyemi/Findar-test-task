package com.findar.bookstore.controller.auth;

import com.findar.bookstore.controller_advice.ZeusException;
import com.findar.bookstore.dto.authenitcationDTO.SignupRequest;
import com.findar.bookstore.service.authservice.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/auth")
@Tag(name="Register Admin")
public class AdminAuthController {


    private final AuthService authServiceImpl;



    @Operation(summary = "An end point to register a user with an admin role. A passcode must be provided in the DTO to create a user with such role" +
            "the passcode is - I love God")
    @PostMapping("/register")
    public ResponseEntity<Object> registerAdmin(@Valid @RequestBody SignupRequest signupRequest) throws ZeusException {

        return ResponseEntity.ok(authServiceImpl.registerAdmin(signupRequest));
    }

}
