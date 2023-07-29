package com.productShop.inventarization.service;

import com.productShop.inventarization.DTO.SupplyDTO;
import com.productShop.inventarization.common.validator.ProductStockValidator;
import com.productShop.inventarization.model.ProductStock;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductSockUtilService {
    private final ProductStockService productStockService;

    public List<ProductStock> createSupply(SupplyDTO supplyDTO) {
        final var productStocks = supplyDTO.getProducts().stream()
                .filter(pr -> pr.getOrderAmount() > 0)
                .filter(pr -> ProductStockValidator.isProductStockAmountEnough(pr.getProductStock(), pr.getOrderAmount()))
                .map(pr -> pr.getProductStock().toBuilder().amount(pr.getOrderAmount() + pr.getProductStock().getAmount()).build())
                .toList();

        return productStockService.saveAllProductStock(productStocks);
    }

    public ProductStock sellProduct(long productStockId, double sellAmount) {
        final var productStock = productStockService.getProductStockById(productStockId);
        final var updatedStock = productStock.toBuilder().amount(productStock.getAmount() - sellAmount).build();
        return productStockService.updateProductStock(updatedStock);
    }
}
