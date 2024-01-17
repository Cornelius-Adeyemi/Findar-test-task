package com.findar.bookstore.dto.responseObject;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class SuccessfulResponse <T>{


    private String status = "Request successfully Treated ";

    private T data;

    public SuccessfulResponse( T data){
        this.data = data;
    }
}
