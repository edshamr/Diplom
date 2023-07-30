package com.productShop.inventarization.controller;

import com.productShop.inventarization.DTO.ProductOrderDTO;
import com.productShop.inventarization.DTO.SupplyDTO;
import com.productShop.inventarization.model.Product;
import com.productShop.inventarization.repos.ProductCategoryRepository;
import com.productShop.inventarization.service.ProductService;
import com.productShop.inventarization.service.ProductSockUtilService;
import com.productShop.inventarization.service.ProductStockService;
import java.util.Map;
import java.util.TreeMap;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class MainController {
    private final ProductCategoryRepository productCategoryRepository;
    ProductService productService;
    ProductStockService productStockService;
    ProductSockUtilService productSockUtilService;

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
        final var productStocks = productSockUtilService.createSupply(supplyDTO);
        // Save to db
        model.addAttribute("supplyDTO", supplyDTO);
        // make page, and redirect to that page (supply-result)
        return "supply-creation";
    }

    @GetMapping("/product-page/{id}")
    public String productPage(@PathVariable("id") long id, Model model) {
        final var product = productService.getProductById(id);
        System.out.println(product.getCategories());
        model.addAttribute("product", product);
        final var allCategories = productCategoryRepository.findAll();
        model.addAttribute("allCategories", allCategories);

        Map<String, Integer> graphData = new TreeMap<>();
        graphData.put("2016", 147);
        graphData.put("2017", 1256);
        graphData.put("2018", 3856);
        graphData.put("2019", 19807);
        model.addAttribute("chartData", graphData);

        return "product-page";
    }

    @PostMapping("/update-product")
    public String updateProduct(Product product) {
        productService.updateProduct(product);
        return "redirect:/product-page/" + product.getId();
    }

    @PostMapping("/add-sell")
    public String createSupply(@RequestParam(name = "sellAmount") double sellAmount,
                               @RequestParam(name = "productStockId") long productStockId, Model model) {
        System.out.println(sellAmount);
        System.out.println(productStockId);
        final var productStock = productSockUtilService.sellProduct(productStockId, sellAmount);
        // get productStock by id from service
        // validate size of sell
        // -amount, and add to history of sells
        return "redirect:/table";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
