package com.productShop.inventarization.service;

import com.productShop.inventarization.model.Product;
import com.productShop.inventarization.model.dto.CreateProductDto;
import com.productShop.inventarization.model.dto.UpdateProductDto;
import jakarta.annotation.Nonnull;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts(String consumerToken);

    Product getProductById(@Nonnull final Long id, String consumerToken);

    Product createProduct(@Nonnull @ModelAttribute("product") final CreateProductDto dto, String consumerToken);

    Product updateProduct(@Nonnull @ModelAttribute("product") final UpdateProductDto productDto, String consumerToken);

    void deleteProduct(@Nonnull final Long id, final String consumerToken);
}
