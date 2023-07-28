package com.productShop.inventarization.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class MainController {

    @GetMapping("/")
    public String helloWorld() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
