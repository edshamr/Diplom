package com.productShop.inventarization.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.productShop.inventarization.model.ProductCategory;
import com.productShop.inventarization.model.dto.AddCategoryDto;
import com.productShop.inventarization.model.dto.CreateProductDto;
import com.productShop.inventarization.repos.ProductCategoryRepository;
import com.productShop.inventarization.service.ProductCategoryService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;

import static com.productShop.inventarization.service.ConsumerTokenUtil.getUserToken;

@Controller
@RequestMapping("/category")
@AllArgsConstructor
public class ProductCategoryController {
    private final ProductCategoryService productCategoryService;
    private final ProductCategoryRepository productCategoryRepository;


    @GetMapping(value = "/categories", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<ProductCategory> getAllCategories(HttpSession session) {
        String token = getUserToken(session);

        return productCategoryService.getAllProductCategories(token);
    }

    @PostMapping("/add-category")
    public void addProductCategory(@RequestBody AddCategoryDto category, HttpSession session, Model model, HttpServletResponse response) {
        String token = getUserToken(session);
        productCategoryService.createProductCategory(category, token);

        final var allCategories = productCategoryRepository.findAll();
        model.addAttribute("allCategories", allCategories);

        response.setContentType("application/json");
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(response.getWriter(), allCategories);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
