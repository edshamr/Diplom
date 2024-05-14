package com.productShop.inventarization.service;

import com.productShop.inventarization.model.Consumer;
import com.productShop.inventarization.model.OperationType;
import com.productShop.inventarization.model.ProductHistory;
import com.productShop.inventarization.model.ProductOrder;
import com.productShop.inventarization.model.ProductStock;
import com.productShop.inventarization.model.SupplyOrder;
import com.productShop.inventarization.model.dto.SupplyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductStockUtilServiceImpl implements ProductStockUtilService {
    private final ProductStockServiceImpl productStockService;
    private final ProductHistoryServiceImpl productHistoryService;
    private final ConsumerService consumerService;

    private final SupplyOrderServiceImpl supplyOrderService;

    @Override
    public SupplyOrder createSupply(SupplyDto supplyDTO, String consumerKey) {
        final var productStocks = supplyDTO.getProducts().stream()
                .filter(pr -> pr.getOrderAmount() > 0)
                .map(productOrderDTO -> ProductOrder.builder()
                        .product(productOrderDTO.getProductStock().getProduct())
                        .orderAmount(productOrderDTO.getOrderAmount())
                        .arrived(false)
                        .build())
                .collect(Collectors.toList());

        Consumer consumer = consumerService.getConsumerByKey(consumerKey);

        final var supplyOrder = SupplyOrder.builder()
                .products(productStocks)
                .date(LocalDate.now())
                .consumer(consumer)
                .build();

        return supplyOrderService.createSupplyOrder(supplyOrder);
    }

    @Override
    @Transactional
    public ProductStock sellProduct(long productStockId, double sellAmount, String consumerKey) {
        final var productStock = productStockService.getProductStockById(productStockId, consumerKey);
        final var updatedStock = productStock.toBuilder().amount(productStock.getAmount() - sellAmount).build();
        productHistoryService.createProductHistory(
                ProductHistory.builder()
                        .product(productStock.getProduct())
                        .amount(sellAmount)
                        .date(LocalDate.now())
                        .soldPrice(productStock.getProduct().getPrice())
                        .operation(OperationType.SALE)
                        .consumer(consumerService.getConsumerByKey(consumerKey))
                        .build()
        );

        return productStockService.updateProductStock(updatedStock, consumerKey);
    }

    @Override
    @Transactional
    public ProductStock writeOffProductAmount(long productStockId, double writeOffAmount, String consumerKey) {
        final var productStock = productStockService.getProductStockById(productStockId, consumerKey);
        final var updatedStock = productStock.toBuilder().amount(productStock.getAmount() - writeOffAmount).build();
        productHistoryService.createProductHistory(
                ProductHistory.builder()
                        .product(productStock.getProduct())
                        .amount(writeOffAmount)
                        .date(LocalDate.now())
                        .soldPrice(productStock.getProduct().getPrice())
                        .operation(OperationType.WRITE_OFF)
                        .consumer(consumerService.getConsumerByKey(consumerKey))
                        .build()
        );
        return productStockService.updateProductStock(updatedStock, consumerKey);
    }

    @Override
    @Transactional
    public ProductStock addAmountToProduct(long productStockId, double toAddAmount, String consumerKey) {
        final var productStock = productStockService.getProductStockByProductId(productStockId, consumerKey);
        final var updatedStock = productStock.toBuilder().amount(productStock.getAmount() + toAddAmount).build();
        productHistoryService.createProductHistory(
                ProductHistory.builder()
                        .product(productStock.getProduct())
                        .amount(toAddAmount)
                        .date(LocalDate.now())
                        .soldPrice(productStock.getProduct().getPrice())
                        .operation(OperationType.SUPPLY)
                        .consumer(consumerService.getConsumerByKey(consumerKey))
                        .build());
        return productStockService.updateProductStock(updatedStock, consumerKey);
    }
}
