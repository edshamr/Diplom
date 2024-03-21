package com.productShop.inventarization.controller;

import com.productShop.inventarization.model.dto.ProductOrderDto;
import com.productShop.inventarization.model.dto.SupplyDto;
import com.productShop.inventarization.model.Product;
import com.productShop.inventarization.model.ProductStock;
import com.productShop.inventarization.model.SupplyOrder;
import com.productShop.inventarization.repos.ProductCategoryRepository;
import com.productShop.inventarization.repos.SupplyOrderRepository;
import com.productShop.inventarization.service.GraphService;
import com.productShop.inventarization.service.ProductService;
import com.productShop.inventarization.service.ProductStockService;
import com.productShop.inventarization.service.ProductStockUtilService;
import com.productShop.inventarization.service.SupplyOrderService;
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
    private final SupplyOrderRepository supplyOrderRepository;
    private final SupplyOrderService supplyOrderService;
    ProductService productService;
    ProductStockService productStockService;
    ProductStockUtilService productStockUtilService;
    GraphService graphService;

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
        var productOrderDto = productStocksList.stream().map((e) -> new ProductOrderDto(e, 0)).toList();
        var supplyDTO = new SupplyDto(productOrderDto);
        model.addAttribute("supplyDTO", supplyDTO);
        return "supply-creation";
    }

    @PostMapping("/create-supply")
    public String createSupply(SupplyDto supplyDTO, Model model) {
        final var supplyOrder = productStockUtilService.createSupply(supplyDTO);
        model.addAttribute("supplyOrder", supplyOrder);
        return "supply-result";
    }

    @PostMapping("/update-supply")
    public String updateSupply(SupplyOrder supplyOrder, Model model) {
        supplyOrderService.updateSupplyOrder(supplyOrder);
        return "redirect:/all-supplies";
    }

    @GetMapping("/all-supplies")
    public String getAllSupplies(Model model) {
        // TODO: Write service
        final var allSupplies = supplyOrderRepository.findAll();
        model.addAttribute("supplies", allSupplies);
        return "all-supply-orders";
    }

    @GetMapping("/supply-page/{id}")
    public String createSupply(@PathVariable("id") long id, Model model) {
        final var supplyOrder =
            supplyOrderRepository.findById(id).orElseThrow(() -> new RuntimeException("Supply order not found"));
        model.addAttribute("supplyOrder", supplyOrder);
        return "supply-result";
    }

    @GetMapping("/product-page/{id}")
    public String productPage(@PathVariable("id") long id, Model model) {
        final var product = productService.getProductById(id);
        model.addAttribute("product", product);
        final var allCategories = productCategoryRepository.findAll();
        model.addAttribute("allCategories", allCategories);

        final var graphData = graphService.generateItemSellHistoryGraph(product.getId());
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
        productStockUtilService.sellProduct(productStockId, sellAmount);
        return "redirect:/table";
    }

    @PostMapping("/write-off")
    public String writeOffProductAmount(@RequestParam(name = "writeOffAmount") double writeOffAmount,
                                        @RequestParam(name = "productStockIdToWriteOff") long productStockIdToWriteOff,
                                        Model model) {
        productStockUtilService.writeOffProductAmount(productStockIdToWriteOff, writeOffAmount);
        return "redirect:/table";
    }

    @PostMapping("/add-amount")
    public String addAmountToProduct(@RequestParam(name = "toAddAmount") double toAddAmount,
                                     @RequestParam(name = "productStockIdToAddAmount") long productStockIdToAddAmount,
                                     Model model) {
        productStockUtilService.addAmountToProduct(productStockIdToAddAmount, toAddAmount);
        return "redirect:/table";
    }

    @GetMapping("/add-product")
    public String addProductPage(Model model) {
        model.addAttribute("product", new Product());
        final var allCategories = productCategoryRepository.findAll();
        model.addAttribute("allCategories", allCategories);
        return "add-product";
    }

    @PostMapping("/add-product")
    public String addProduct(Product product) {
        productService.createProduct(product);
        productStockService.saveProductStock(ProductStock.builder()
            .product(product)
            .unitDimension("kg")
            .amount(0)
            .build());
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
