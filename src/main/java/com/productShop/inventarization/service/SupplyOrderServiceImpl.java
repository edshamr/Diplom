package com.productShop.inventarization.service;

import com.productShop.inventarization.exception.SupplyOrderNotFoundException;
import com.productShop.inventarization.model.SupplyOrder;
import com.productShop.inventarization.repos.SupplyOrderRepository;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SupplyOrderServiceImpl implements SupplyOrderService {
    private final SupplyOrderRepository supplyOrderRepository;
    private final ProductStockServiceImpl productStockService;

    @Override
    public List<SupplyOrder> getAllSupplyOrders(String consumerKey) {
        return supplyOrderRepository.findAllByAndConsumer_Key(consumerKey);
    }

    @Override
    public SupplyOrder getSupplyOrderById(@Nonnull final Long id) {
        return supplyOrderRepository.findById(id).
                orElseThrow(() -> new SupplyOrderNotFoundException("Such order was not found"));
    }

    @Override
    public SupplyOrder createSupplyOrder(@Nonnull @ModelAttribute("supply_order") final SupplyOrder supplyOrder) {
        return supplyOrderRepository.save(supplyOrder);
    }

    @Override
    @Transactional
    public SupplyOrder updateSupplyOrder(@Nonnull @ModelAttribute("supply_order") final SupplyOrder supplyOrder,
                                         @Nonnull String consumerKey) {
        SupplyOrder orderToUpdate = supplyOrderRepository.findByIdAndConsumer_Key(supplyOrder.getId(), consumerKey).
                orElseThrow(() -> new SupplyOrderNotFoundException("Such order was not found"));
        supplyOrder.setConsumer(orderToUpdate.getConsumer());

        final var oldOrder = orderToUpdate;
        supplyOrder.getProducts().forEach(
                newProductOrder -> {
                    if (newProductOrder.isArrived()) {
                        final var oldProductOrder =
                                oldOrder.getProducts().stream().filter(productOrder -> Objects.equals(productOrder.getId(),
                                        newProductOrder.getId())).findFirst();
                        if (oldProductOrder.isPresent()) {
                            final var existingOldOrder = oldProductOrder.get();
                            if (!existingOldOrder.isArrived()) {
                                final var productStock =
                                        productStockService.getProductStockByProductId(
                                                newProductOrder.getProduct().getId(), consumerKey);
                                productStock.setAmount(
                                        productStock.getAmount() + newProductOrder.getOrderAmount()
                                );
                                productStockService.saveProductStock(productStock);
                            }
                        }
                    }
                }
        );
        return supplyOrderRepository.save(supplyOrder);
    }

    @Override
    public void deleteSupplyOrder(@Nonnull final Long id) {
        final var supplyOrderToDelete = supplyOrderRepository.findById(id).
                orElseThrow(() -> new SupplyOrderNotFoundException("Such order was not found"));
        supplyOrderRepository.delete(supplyOrderToDelete);
    }
}
