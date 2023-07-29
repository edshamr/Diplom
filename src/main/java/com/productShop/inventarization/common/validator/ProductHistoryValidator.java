package com.productShop.inventarization.common.validator;

import com.productShop.inventarization.model.ProductHistory;
import org.springframework.stereotype.Service;

@Service
public class ProductHistoryValidator {
    public static boolean isProductHistoryValid(ProductHistory productHistory) {
        final var price = productHistory.getSoldPrice();
        final var amount = productHistory.getAmount();
        return amount < 0 || price < 0;
    }
}
