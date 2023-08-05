package com.productShop.inventarization.service;

import com.productShop.inventarization.exception.SupplyOrderNotFoundException;
import com.productShop.inventarization.model.SupplyOrder;
import com.productShop.inventarization.repos.SupplyOrderRepository;
import jakarta.annotation.Nonnull;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;

@Service
@RequiredArgsConstructor
public class SupplyOrderService {
    private final SupplyOrderRepository supplyOrderRepository;
    private final ProductStockService productStockService;

    public List<SupplyOrder> getAllSupplyOrders() {
        return supplyOrderRepository.findAll();
    }

    public SupplyOrder getSupplyOrderById(@Nonnull final Long id) {
        return supplyOrderRepository.findById(id).
            orElseThrow(() -> new SupplyOrderNotFoundException("Such order was not found"));
    }

    public SupplyOrder createSupplyOrder(@Nonnull @ModelAttribute("supply_order") final SupplyOrder supplyOrder) {
        return supplyOrderRepository.save(supplyOrder);
    }

    @Transactional
    public SupplyOrder updateSupplyOrder(@Nonnull @ModelAttribute("supply_order") final SupplyOrder supplyOrder) {
        final var oldOrder = supplyOrderRepository.findById(supplyOrder.getId()).
            orElseThrow(() -> new SupplyOrderNotFoundException("Such order was not found"));
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
                                productStockService.getProductStockByProductId(newProductOrder.getProduct().getId());
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

    public void deleteSupplyOrder(@Nonnull final Long id) {
        final var supplyOrderToDelete = supplyOrderRepository.findById(id).
            orElseThrow(() -> new SupplyOrderNotFoundException("Such order was not found"));
        supplyOrderRepository.delete(supplyOrderToDelete);
    }
}
