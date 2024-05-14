package com.productShop.inventarization.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(RuntimeException.class)
    public String allExceptions(Model model, RuntimeException ex) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error";
    }
}
