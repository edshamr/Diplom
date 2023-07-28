package com.productShop.inventarization.exception;

import java.io.Serial;

public class ProductHistoryNotFoundException extends NotFoundException {
    @Serial
    private static final long serialVersionUID = -2831541100572093185L;

    public ProductHistoryNotFoundException(String message) {
        super(message);
    }
}
