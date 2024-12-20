package com.revature.aspects;

import jakarta.servlet.http.HttpSession;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

// An ASPECT is a class that can trigger functionality at any point in our application
//when a certain method is called this class can listen for that and trigger some method
@Aspect
@Component
public class AuthAspect {
    // Runs after any controller method call except login or register
    @Before("within(com.revature.controllers.*) " +
            "&& !execution(* com.revature.controllers.AuthController.login(..))" +
            "&& !execution(* com.revature.controllers.UserController.registerUser(..))")
    public void checkLoggedIn(){

        // Get session or lack there of
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession(false);

        //Check if the session exists and if the userId has been set in the session
        if (session == null || session.getAttribute("userId") == null) {
            throw new IllegalArgumentException("User is not logged in");
        }
    }

    @Before("@annotation(ManagerOnly)")
    public void checkAdmin(){
        checkLoggedIn();

        if(!"manager".equals(((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest().getSession().getAttribute("role"))){

            throw new IllegalArgumentException("User is not a manager!");

        }

        //This is doing what I did in checkLogin, but as a one liner

    }
}
