package com.productShop.inventarization.service;

import com.productShop.inventarization.common.validator.ProductStockValidator;
import com.productShop.inventarization.exception.ProductStockNotFoundException;
import com.productShop.inventarization.model.ProductStock;
import com.productShop.inventarization.repos.ProductStockRepository;
import jakarta.annotation.Nonnull;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

@Service
@RequiredArgsConstructor
public class ProductStockService {
    private final ProductStockRepository productStockRepository;

    public List<ProductStock> getAllProductStock() {
        return productStockRepository.findAll();
    }

    public ProductStock getProductStockById(@Nonnull final Long id) {
        return productStockRepository.findById(id).
                orElseThrow(() -> new ProductStockNotFoundException("Such product stock was not found"));
    }

    public ProductStock saveProductStock(@Nonnull @ModelAttribute("product_stock") final ProductStock productStock) {
        if (ProductStockValidator.isProductStockValid(productStock)) {
            throw new RuntimeException("Such product stock is not valid");
        }

        return productStockRepository.save(productStock);
    }

    public List<ProductStock> saveAllProductStock(
            @Nonnull @ModelAttribute("product_stock") final List<ProductStock> productStock) {
        return productStockRepository.saveAll(productStock);
    }

    public ProductStock updateProductStock(@Nonnull @ModelAttribute("product_stock") final ProductStock productStock) {
        if (ProductStockValidator.isProductStockValid(productStock)) {
            throw new RuntimeException("Such product stock is not valid");
        }

        productStockRepository.findById(productStock.getId()).
                orElseThrow(() -> new ProductStockNotFoundException("Such product stock was not found"));
        return productStockRepository.save(productStock);
    }

    public void deleteProductStock(@Nonnull final Long id) {
        final var productStockToDelete = productStockRepository.findById(id).
                orElseThrow(() -> new ProductStockNotFoundException("Such product stock was not found"));
        productStockRepository.delete(productStockToDelete);
    }
}
