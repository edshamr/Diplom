package com.productShop.inventarization.service;

import com.productShop.inventarization.DTO.SupplyDTO;
import com.productShop.inventarization.common.validator.ProductStockValidator;
import com.productShop.inventarization.model.ProductHistory;
import com.productShop.inventarization.model.ProductStock;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductStockUtilService {
    private final ProductStockService productStockService;
    private final ProductHistoryService productHistoryService;

    public List<ProductStock> createSupply(SupplyDTO supplyDTO) {
        final var productStocks = supplyDTO.getProducts().stream()
                .filter(pr -> pr.getOrderAmount() > 0)
                .filter(pr -> ProductStockValidator.isProductStockAmountEnough(pr.getProductStock(), pr.getOrderAmount()))
                .map(pr -> pr.getProductStock().toBuilder().amount(pr.getOrderAmount() + pr.getProductStock().getAmount()).build())
                .toList();

        return productStockService.saveAllProductStock(productStocks);
    }

    @Transactional
    public ProductStock sellProduct(long productStockId, double sellAmount) {
        final var productStock = productStockService.getProductStockById(productStockId);
        final var updatedStock = productStock.toBuilder().amount(productStock.getAmount() - sellAmount).build();
        productHistoryService.createProductHistory(
            ProductHistory.builder()
                .product(productStock.getProduct())
                .amount(sellAmount)
                .date(LocalDate.now())
                .soldPrice(productStock.getProduct().getPrice())
            .build()
        );

        return productStockService.updateProductStock(updatedStock);
    }
}
