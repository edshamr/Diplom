package com.productShop.inventarization.controller;

import com.productShop.inventarization.DTO.ProductOrderDTO;
import com.productShop.inventarization.service.ProductService;
import com.productShop.inventarization.service.ProductStockService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class MainController {
    ProductService productService;
    ProductStockService productStockService;

    @GetMapping("/")
    public String mainPage(Model model) {
        var productsList = productService.getAllProducts();
        model.addAttribute("products", productsList);
        return "index";
    }

    @GetMapping("/table")
    public String tableViewPage(Model model) {
        var productStocksList = productStockService.getAllProductStock();
        model.addAttribute("products", productStocksList);
        return "table";
    }

    @GetMapping("/supply")
    public String getSupplyRequestPage(Model model) {
        var productStocksList = productStockService.getAllProductStock();
        var productOrderDto = productStocksList.stream().map((e) -> new ProductOrderDTO(e, 0)).toList();
        System.out.println(productOrderDto);
        model.addAttribute("productsDto", productOrderDto);
        return "supply-creation";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
