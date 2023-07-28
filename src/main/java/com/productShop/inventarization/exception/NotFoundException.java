package com.productShop.inventarization.exception;

import java.io.Serial;

public class NotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1592379446804674651L;

    public NotFoundException(String message) {
        super(message);
    }
}
