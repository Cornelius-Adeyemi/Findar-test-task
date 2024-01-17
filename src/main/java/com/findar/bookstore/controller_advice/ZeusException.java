package com.findar.bookstore.controller_advice;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;


@Getter
@Setter
public class ZeusException extends Exception{

    private HttpStatus httpStatus;

    private String responseCode;

    private Object data;


    public ZeusException(String message){
        super(message);
    }



    public ZeusException(HttpStatus httpStatus, String responseCode, Object data ){
        this.httpStatus = httpStatus;
        this.responseCode = responseCode;
        this.data=data;
    }
}
