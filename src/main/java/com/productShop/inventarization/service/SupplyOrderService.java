package com.productShop.inventarization.service;

import com.productShop.inventarization.model.SupplyOrder;
import jakarta.annotation.Nonnull;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

public interface SupplyOrderService {
    List<SupplyOrder> getAllSupplyOrders(String consumerKey);

    SupplyOrder getSupplyOrderById(@Nonnull final Long id);

    SupplyOrder createSupplyOrder(@Nonnull @ModelAttribute("supply_order") final SupplyOrder supplyOrder);

    SupplyOrder updateSupplyOrder(@Nonnull @ModelAttribute("supply_order") final SupplyOrder supplyOrder,
                                  @Nonnull String consumerKey);

    void deleteSupplyOrder(@Nonnull final Long id);
}
