package com.productShop.inventarization.controller;

import com.productShop.inventarization.model.SupplyOrder;
import com.productShop.inventarization.model.dto.ProductHistorySupplyProjection;
import com.productShop.inventarization.service.ProductHistoryServiceImpl;
import com.productShop.inventarization.service.SupplyOrderServiceImpl;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDate;
import java.util.List;

import static com.productShop.inventarization.service.ConsumerTokenUtil.getUserToken;

@Controller
@RequestMapping("/supply")
@AllArgsConstructor
public class SupplyOrderController {
    private final SupplyOrderServiceImpl supplyOrderService;
    private final ProductHistoryServiceImpl productHistoryService;

    @GetMapping("/all-supplies")
    public String getAllSupplies(Model model, HttpSession session) {
        String token = getUserToken(session);
        List<ProductHistorySupplyProjection> histories = productHistoryService.getAllProductSupplyHistories(token);
        model.addAttribute("histories", histories);
        return "all-orders-history";
    }

    @PostMapping("/all-supplies")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<ProductHistorySupplyProjection> getAllSupplies(
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate,
            HttpSession session) {
        String token = getUserToken(session);
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        return productHistoryService.getAllProductSupplyHistoriesAndDate(token, start, end);
    }

    @PostMapping("/update-supply")
    public String updateSupply(SupplyOrder supplyOrder, HttpSession session) {
        String token = getUserToken(session);
        supplyOrderService.updateSupplyOrder(supplyOrder, token);
        return "redirect:/api/all-supplies";
    }
}
