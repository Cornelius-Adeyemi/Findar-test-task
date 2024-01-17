package com.findar.bookstore.controller.auth;


import com.findar.bookstore.controller_advice.ZeusException;
import com.findar.bookstore.dto.authenitcationDTO.AuthenticationRequest;
import com.findar.bookstore.dto.authenitcationDTO.SignupRequest;
import com.findar.bookstore.service.authservice.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user/auth")
@Tag(name="User/Admin Auth")
public class AuthController {

    private final AuthService authServiceImpl;



    @Operation(summary = "An end point to login")
    @PostMapping("/login")
    public ResponseEntity<Object> login(@Valid @RequestBody AuthenticationRequest authenticationRequest) throws Exception {

        return ResponseEntity.ok(authServiceImpl.login(authenticationRequest));

    }

    @Operation(summary = "An end point to register a user with user role")
    @PostMapping("/register")
    public ResponseEntity<Object> register(@Valid @RequestBody SignupRequest signupRequest) throws ZeusException {

        return ResponseEntity.ok(authServiceImpl.register(signupRequest));
    }

}
