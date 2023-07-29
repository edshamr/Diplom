package com.productShop.inventarization.exception;

import java.io.Serial;

public class SupplyOrderNotFoundException extends NotFoundException {
    @Serial
    private static final long serialVersionUID = -6938902121583652725L;

    public SupplyOrderNotFoundException(String message) {
        super(message);
    }
}
