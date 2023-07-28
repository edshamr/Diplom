package com.productShop.inventarization.exception;

import java.io.Serial;

public class ProductStockNotFoundException extends NotFoundException {
    @Serial
    private static final long serialVersionUID = 5691130916421703770L;

    public ProductStockNotFoundException(String message) {
        super(message);
    }
}
