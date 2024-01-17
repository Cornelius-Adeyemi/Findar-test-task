package com.findar.bookstore.service.authservice;


import com.findar.bookstore.config.JwtService;
import com.findar.bookstore.controller_advice.ZeusException;
import com.findar.bookstore.dto.authenitcationDTO.AuthenticationRequest;
import com.findar.bookstore.dto.authenitcationDTO.SignupRequest;
import com.findar.bookstore.dto.responseObject.SuccessfulResponse;
import com.findar.bookstore.enum_package.Role;
import com.findar.bookstore.model.RootUser;
import com.findar.bookstore.repository.RootUserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.HashMap;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {


    private final RootUserRepo rootUserRepo;

    private final AuthenticationManager authenticationManager;

    private final UserDetailsService userDetailsService;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    @Value("${admin.pass.phrase}")
    private String adminPasscode;

    public Object login(AuthenticationRequest authenticationRequest) throws Exception {

       log.info("-----login----");//
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),
                authenticationRequest.getPassword());

        try {
            //check if credentials are correct
            authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        }catch (BadCredentialsException e){
            //change to custom endpoint
            throw new ZeusException(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.toString(),"Invalid login credentials");
        }


        UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
      // generate token
        String token = jwtService.generateToken(userDetails);

        HashMap<String, String> data = new HashMap<>();
        data.put("token", token);

        return new SuccessfulResponse<Object>(data);


    }


    public Object register(SignupRequest signupRequest) throws ZeusException {

        log.info("-----register----");



        Optional<RootUser> optionalUser =  rootUserRepo.findByEmail(signupRequest.getEmail());
        if(optionalUser.isPresent()){// check if email alredy exist in the DB

            throw new ZeusException(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.toString(),"Email already exist");

        }
        //encode password
        String password = passwordEncoder.encode(signupRequest.getPassword());

        RootUser rootUser = new RootUser();

        BeanUtils.copyProperties(signupRequest,rootUser );
        rootUser.setPassword(password);
        rootUser.setRole(Role.USER);
       //save to DB
         rootUserRepo.save(rootUser);



        return new SuccessfulResponse<Object>("account created. You can login now");
    }


    public Object registerAdmin(SignupRequest signupRequest) throws ZeusException {

        log.info("-----register-- admin--");
        byte[] decodedBytes = Base64.getDecoder().decode(adminPasscode);
        String passcode = new String(decodedBytes);
       // check if password is correct
        if(!passcode.equalsIgnoreCase(signupRequest.getAdminPassCode())){
            throw new ZeusException(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.toString(),"Invalid admin passcode");
        }


      // check if email already exist
        Optional<RootUser> optionalUser =  rootUserRepo.findByEmail(signupRequest.getEmail());
        if(optionalUser.isPresent()){

            throw new ZeusException(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.toString(),"Email already exist");

        }
        //encode password
        String password = passwordEncoder.encode(signupRequest.getPassword());

        RootUser rootUser = new RootUser();
       //copy properties
        BeanUtils.copyProperties(signupRequest,rootUser );
        rootUser.setPassword(password);
        rootUser.setRole(Role.ADMIN);

        rootUserRepo.save(rootUser);



        return new SuccessfulResponse<Object>("account created. You can login now");
    }



}
