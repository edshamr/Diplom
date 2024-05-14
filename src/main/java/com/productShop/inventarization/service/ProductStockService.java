package com.productShop.inventarization.service;

import com.productShop.inventarization.model.ProductStock;
import jakarta.annotation.Nonnull;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

public interface ProductStockService {
    List<ProductStock> getAllProductStock();

    ProductStock getProductStockById(@Nonnull final Long id, String consumerKey);

    ProductStock getProductStockByProductId(@Nonnull final Long id, String consumerKey);

    ProductStock saveProductStock(@Nonnull @ModelAttribute("product_stock") final ProductStock productStock);

    List<ProductStock> saveAllProductStock(
            @Nonnull @ModelAttribute("product_stock") final List<ProductStock> productStock);

    ProductStock updateProductStock(@Nonnull @ModelAttribute("product_stock") final ProductStock productStock,
                                    String consumerKey);

    void deleteProductStock(@Nonnull final Long id);
}
