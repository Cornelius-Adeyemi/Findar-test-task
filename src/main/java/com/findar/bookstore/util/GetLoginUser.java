package com.findar.bookstore.util;

import com.findar.bookstore.model.RootUser;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.context.SecurityContextHolder;

public class GetLoginUser {

    public static RootUser checkForCurrentUserId(){

       RootUser rootUser =(RootUser) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        System.out.println("********************+" + rootUser );

        return rootUser;
    }
}
