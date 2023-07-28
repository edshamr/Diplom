package com.productShop.inventarization.exception;

import java.io.Serial;

public class ProductNotFoundException extends NotFoundException {
    @Serial
    private static final long serialVersionUID = 859123450180303713L;

    public ProductNotFoundException(String message) {
        super(message);
    }
}
