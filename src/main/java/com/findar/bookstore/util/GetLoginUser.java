package com.findar.bookstore.util;

import com.findar.bookstore.model.RootUser;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class GetLoginUser {

    public  RootUser checkForCurrentUserId(){

       RootUser rootUser =(RootUser) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        System.out.println("********************+" + rootUser );

        return rootUser;
    }
}
