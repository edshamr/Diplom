package com.productShop.inventarization.service;

import com.productShop.inventarization.exception.ProductNotFoundException;
import com.productShop.inventarization.model.Product;
import com.productShop.inventarization.repos.ProductRepository;
import jakarta.annotation.Nonnull;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(@Nonnull final Long id) {
        var product = productRepository.findById(id).
            orElseThrow(() -> new ProductNotFoundException("Such product was not found"));
        Hibernate.initialize(product.getCategories());
        return product;
    }

    public Product createProduct(@Nonnull @ModelAttribute("product") final Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(@Nonnull @ModelAttribute("product") final Product product) {
        productRepository.findById(product.getId()).
            orElseThrow(() -> new ProductNotFoundException("Such product was not found"));
        return productRepository.save(product);
    }

    public void deleteProduct(@Nonnull final Long id) {
        final var productToDelete = productRepository.findById(id).
            orElseThrow(() -> new ProductNotFoundException("Such product was not found"));
        productRepository.delete(productToDelete);
    }
}
