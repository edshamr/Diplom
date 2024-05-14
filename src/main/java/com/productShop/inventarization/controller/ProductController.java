package com.productShop.inventarization.controller;

import com.productShop.inventarization.model.Consumer;
import com.productShop.inventarization.model.Product;
import com.productShop.inventarization.model.ProductStock;
import com.productShop.inventarization.model.dto.AddConsumerDto;
import com.productShop.inventarization.model.dto.CreateProductDto;
import com.productShop.inventarization.model.dto.UpdateProductDto;
import com.productShop.inventarization.repos.ProductCategoryRepository;
import com.productShop.inventarization.service.ConsumerService;
import com.productShop.inventarization.service.GraphServiceImpl;
import com.productShop.inventarization.service.ProductCategoryServiceImpl;
import com.productShop.inventarization.service.ProductServiceImpl;
import com.productShop.inventarization.service.ProductStockServiceImpl;
import com.productShop.inventarization.service.StatisticServiceImpl;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.productShop.inventarization.service.ConsumerTokenUtil.getUserToken;

@Controller
@RequestMapping("/api")
@AllArgsConstructor
public class ProductController {
    private final ProductCategoryRepository productCategoryRepository;
    private final ProductServiceImpl productService;
    private final ProductStockServiceImpl productStockService;
    private final StatisticServiceImpl statisticService;
    private final GraphServiceImpl graphService;
    private final ProductCategoryServiceImpl productCategoryService;
    private final ConsumerService consumerService;

    @GetMapping("/")
    public String mainPage(Model model, HttpSession session) {
        String token = getUserToken(session);

        var productsList = productService.getAllProducts(token);
        model.addAttribute("products", productsList);
        return "index";
    }

    @GetMapping("/table")
    public String tableViewPage(Model model) {
        var productStocksList = productStockService.getAllProductStock();

        model.addAttribute("products", productStocksList);
        return "table";
    }

    @GetMapping("/product-page/{id}")
    public String productPage(@PathVariable("id") long id, Model model, HttpSession session) {
        String token = getUserToken(session);

        final var product = productService.getProductById(id, token);
        model.addAttribute("product", product);
        final var allCategories = productCategoryRepository.findAll();
        model.addAttribute("allCategories", allCategories);

        final var graphData = graphService.generateItemSellHistoryGraph(product.getId(), token);
        model.addAttribute("chartData", graphData);

        return "product-page";
    }

    @PostMapping("/update-product")
    @ResponseBody
    public ResponseEntity<Product> updateProduct(@ModelAttribute UpdateProductDto productDto, HttpSession session) {
        String token = getUserToken(session);
        Product updatedProduct = productService.updateProduct(productDto, token);
        return ResponseEntity.ok(updatedProduct);
    }



    @GetMapping("/add-product")
    public String addProductPage(Model model, HttpSession session) {
        String token = getUserToken(session);

        model.addAttribute("product", new CreateProductDto());
        final var allCategories = productCategoryService.getAllProductCategories(token);
        model.addAttribute("allCategories", allCategories);
        return "add-product";
    }

    @PostMapping("/add-product")
    public String addProduct(@ModelAttribute CreateProductDto product, HttpSession session) {
        String token = getUserToken(session);

        Product savedProduct = productService.createProduct(product, token);
        Consumer consumer = consumerService.getConsumerByKey(token);
        productStockService.saveProductStock(ProductStock.builder()
                .product(savedProduct)
                .unitDimension("kg")
                .amount(0)
                .consumer(consumer)
                .build());
        return "redirect:/api";
    }

    @GetMapping("/profile")
    public String getProfile(Authentication authentication, Model model) {
        String consumerName = authentication.getName();

        Consumer consumer = consumerService.getConsumerByEmail(consumerName);

        model.addAttribute("consumer", consumer);

        return "profile-page";
    }


    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String registerConsumer(@ModelAttribute AddConsumerDto consumerDto) {
        consumerService.addConsumer(consumerDto);
        return "redirect:/api";
    }
}
