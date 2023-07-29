package com.productShop.inventarization.controller;

import com.productShop.inventarization.DTO.ProductOrderDTO;
import com.productShop.inventarization.DTO.SupplyDTO;
import com.productShop.inventarization.repos.ProductCategoryRepository;
import com.productShop.inventarization.service.ProductService;
import com.productShop.inventarization.service.ProductStockService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class MainController {
    private final ProductCategoryRepository productCategoryRepository;
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
        var supplyDTO = new SupplyDTO(productOrderDto);
        model.addAttribute("supplyDTO", supplyDTO);
        return "supply-creation";
    }

    @PostMapping("/create-supply")
    public String createSupply(SupplyDTO supplyDTO, Model model) {
        supplyDTO.getProducts().forEach(System.out::println);
        // Save to db
        model.addAttribute("supplyDTO", supplyDTO);
        // make page, and redirect to that page (supply-result)
        return "supply-creation";
    }

    @GetMapping("/product-page/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model) {
        final var product = productService.getProductById(id);
        System.out.println(product.getCategories());
        model.addAttribute("product", product);
        final var allCategories = productCategoryRepository.findAll();
        model.addAttribute("allCategories", allCategories);
        return "product-page";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
