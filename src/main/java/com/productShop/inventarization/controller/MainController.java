package com.productShop.inventarization.controller;

import com.productShop.inventarization.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class MainController {
    ProductService productService;

    @GetMapping("/")
    public String mainPage(Model model) {
        var productsList = productService.getAllProducts();
        model.addAttribute("products", productsList);
        return "index";
    }

    @GetMapping("/table")
    public String tableViewPage(Model model) {
        var productsList = productService.getAllProducts();
        model.addAttribute("products", productsList);
        return "table";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
