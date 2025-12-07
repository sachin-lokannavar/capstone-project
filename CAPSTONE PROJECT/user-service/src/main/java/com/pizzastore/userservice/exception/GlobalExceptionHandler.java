package com.pizzastore.userservice.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    
    @ExceptionHandler(Exception.class)
    public String handleGeneralException(Exception e, Model model) {
        
        System.out.println(">>> ERROR CAUGHT: " + e.getMessage());                
        model.addAttribute("errorMessage", e.getMessage());                
        return "error";
    }
}