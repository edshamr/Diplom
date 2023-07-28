package com.productShop.inventarization.common.validator;

import com.productShop.inventarization.model.ProductStock;
import org.springframework.stereotype.Service;

@Service
public class ProductStockValidator {
    public static boolean isProductStockValid(ProductStock productStock) {
        final var amount = productStock.getAmount();
        return amount < 0;
    }
}
