package com.findar.bookstore.controller_advice;


import com.findar.bookstore.dto.responseObject.FailureResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice

public class ExceptionController {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleDTOFieldException(MethodArgumentNotValidException e){

        Map<String, String> errorObject = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(err-> errorObject.put(err.getField(), err.getDefaultMessage()));

        return new ResponseEntity<>(new FailureResponse<>(HttpStatus.BAD_REQUEST.toString(),errorObject),HttpStatus.BAD_REQUEST );
    }


    @ExceptionHandler(ZeusException.class)
    public ResponseEntity<Object> zeusExceptionHandler(ZeusException e){


        return new ResponseEntity<>(new FailureResponse<>(e.getResponseCode() ,e.getData()),e.getHttpStatus() );
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> ExceptionHandler(Exception e){

        return new ResponseEntity<>(new FailureResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.toString() ,"Please contact support"),HttpStatus.INTERNAL_SERVER_ERROR );
    }


}
