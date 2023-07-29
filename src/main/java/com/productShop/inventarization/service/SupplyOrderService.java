package com.productShop.inventarization.service;

import com.productShop.inventarization.DTO.SupplyDTO;
import com.productShop.inventarization.common.validator.ProductStockValidator;
import com.productShop.inventarization.exception.SupplyOrderNotFoundException;
import com.productShop.inventarization.model.SupplyOrder;
import com.productShop.inventarization.repos.SupplyOrderRepository;
import jakarta.annotation.Nonnull;

import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hibernate.internal.util.collections.CollectionHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ModelAttribute;

@Service
@RequiredArgsConstructor
public class SupplyOrderService {
    private final SupplyOrderRepository supplyOrderRepository;
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

    public SupplyOrder updateSupplyOrder(@Nonnull @ModelAttribute("supply_order") final SupplyOrder supplyOrder) {
        supplyOrderRepository.findById(supplyOrder.getId()).
            orElseThrow(() -> new SupplyOrderNotFoundException("Such order was not found"));
        return supplyOrderRepository.save(supplyOrder);
    }

    public void deleteSupplyOrder(@Nonnull final Long id) {
        final var supplyOrderToDelete = supplyOrderRepository.findById(id).
            orElseThrow(() -> new SupplyOrderNotFoundException("Such order was not found"));
        supplyOrderRepository.delete(supplyOrderToDelete);
    }
}
