package com.productShop.inventarization.service;

import com.productShop.inventarization.model.Product;
import com.productShop.inventarization.repos.ProductRepository;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.nio.file.ProviderNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(@Nonnull final Long id) {
        return productRepository.findById(id).
                orElseThrow(() -> new ProviderNotFoundException("Such product was not found"));
    }

    public Product createProduct(@Nonnull @ModelAttribute("product") final Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(@Nonnull @ModelAttribute("product") final Product product) {
        productRepository.findById(product.getId()).
                orElseThrow(() -> new ProviderNotFoundException("Such product was not found"));
        return productRepository.save(product);
    }

    public void deleteProduct(@Nonnull final Long id) {
        final var productToDelete = productRepository.findById(id).
                orElseThrow(() -> new ProviderNotFoundException("Such product was not found"));
        productRepository.delete(productToDelete);
    }
}
