package com.productShop.inventarization.controller;

import com.productShop.inventarization.service.ProductStockServiceImpl;
import com.productShop.inventarization.service.ProductStockUtilServiceImpl;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.productShop.inventarization.service.ConsumerTokenUtil.getUserToken;

@Controller
@RequestMapping("/stock")
@AllArgsConstructor
public class ProductStockController {
    private final ProductStockUtilServiceImpl productStockUtilService;

    @PostMapping("/add-sell")
    public String createSupply(@RequestParam(name = "sellAmount") double sellAmount,
                               @RequestParam(name = "productStockId") long productStockId,
                               HttpSession session) {
        String token = getUserToken(session);

        productStockUtilService.sellProduct(productStockId, sellAmount, token);
        return "redirect:/api/table";
    }

    @PostMapping("/write-off")
    public String writeOffProductAmount(@RequestParam(name = "writeOffAmount") double writeOffAmount,
                                        @RequestParam(name = "productStockIdToWriteOff") long productStockIdToWriteOff,
                                        HttpSession session) {
        String token = getUserToken(session);
        productStockUtilService.writeOffProductAmount(productStockIdToWriteOff, writeOffAmount, token);
        return "redirect:/api/table";
    }

    @PostMapping("/add-amount")
    public String addAmountToProduct(@RequestParam(name = "toAddAmount") double toAddAmount,
                                     @RequestParam(name = "productStockIdToAddAmount") long productStockIdToAddAmount,
                                     HttpSession session) {
        String token = getUserToken(session);
        productStockUtilService.addAmountToProduct(productStockIdToAddAmount, toAddAmount, token);
        return "redirect:/api/table";
    }
}
