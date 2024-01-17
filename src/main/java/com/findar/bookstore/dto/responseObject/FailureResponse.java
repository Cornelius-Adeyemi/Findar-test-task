package com.findar.bookstore.dto.responseObject;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FailureResponse<T> {

    private String message = "failure processing request";

    private String responseCode;


    private T data;

    public FailureResponse( String responseCode, T data ){
        this.responseCode =responseCode;
        this.data= data;

    }

}
