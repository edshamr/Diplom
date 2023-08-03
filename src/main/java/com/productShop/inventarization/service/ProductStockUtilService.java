package com.productShop.inventarization.service;

import com.productShop.inventarization.DTO.SupplyDTO;
import com.productShop.inventarization.model.ProductHistory;
import com.productShop.inventarization.model.ProductOrder;
import com.productShop.inventarization.model.ProductStock;
import com.productShop.inventarization.model.SupplyOrder;
import java.time.LocalDate;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductStockUtilService {
    private final ProductStockService productStockService;
    private final ProductHistoryService productHistoryService;

    private final SupplyOrderService supplyOrderService;

    public SupplyOrder createSupply(SupplyDTO supplyDTO) {
        final var productStocks = supplyDTO.getProducts().stream()
            .filter(pr -> pr.getOrderAmount() > 0)
            .map(productOrderDTO -> ProductOrder.builder()
                .product(productOrderDTO.getProductStock().getProduct())
                .orderAmount(productOrderDTO.getOrderAmount())
                .arrived(false)
                .build())
            .collect(Collectors.toList());
        final var supplyOrder = SupplyOrder.builder()
            .products(productStocks)
            .date(LocalDate.now())
            .build();

        return supplyOrderService.createSupplyOrder(supplyOrder);
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

    @Transactional
    public ProductStock writeOffProductAmount(long productStockId, double sellAmount) {
        final var productStock = productStockService.getProductStockById(productStockId);
        final var updatedStock = productStock.toBuilder().amount(productStock.getAmount() - sellAmount).build();
        return productStockService.updateProductStock(updatedStock);
    }

    @Transactional
    public ProductStock addAmountToProduct(long productStockId, double toAddAmount) {
        final var productStock = productStockService.getProductStockById(productStockId);
        final var updatedStock = productStock.toBuilder().amount(productStock.getAmount() + toAddAmount).build();
        return productStockService.updateProductStock(updatedStock);
    }

}
