package com.productShop.inventarization.exception;

import java.io.Serial;

public class ProductCategoryNotFoundException extends NotFoundException {
    @Serial
    private static final long serialVersionUID = 8626821911187669718L;

    public ProductCategoryNotFoundException(String message) {
        super(message);
    }
}
