package com.productShop.inventarization.service;

import com.productShop.inventarization.DTO.ProductHistoryProjection;
import com.productShop.inventarization.common.validator.ProductHistoryValidator;
import com.productShop.inventarization.exception.ProductHistoryNotFoundException;
import com.productShop.inventarization.model.ProductHistory;
import com.productShop.inventarization.repos.ProductHistoryRepository;
import jakarta.annotation.Nonnull;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

@Service
@RequiredArgsConstructor
public class ProductHistoryService {
    private final ProductHistoryRepository productHistoryRepository;

    public List<ProductHistory> getAllProductHistories() {
        return productHistoryRepository.findAll();
    }

    public ProductHistory getProductHistoryById(@Nonnull final Long id) {
        return productHistoryRepository.findById(id).
            orElseThrow(() -> new ProductHistoryNotFoundException("Such product history was not found"));
    }

    public List<ProductHistoryProjection> getProductHistoryByProductId(@Nonnull final Long id) {
        return productHistoryRepository.findProductHistoriesByProductId(id);
    }

    public ProductHistory createProductHistory(
        @Nonnull @ModelAttribute("product_history") final ProductHistory productHistory) {
        if (ProductHistoryValidator.isProductHistoryValid(productHistory)) {
            throw new RuntimeException("Such product history is not valid.");
        }

        return productHistoryRepository.save(productHistory);
    }

    public ProductHistory updateProductHistory(
        @Nonnull @ModelAttribute("product_history") final ProductHistory productHistory) {
        if (ProductHistoryValidator.isProductHistoryValid(productHistory)) {
            throw new RuntimeException("Such product history is not valid.");
        }

        productHistoryRepository.findById(productHistory.getId()).
            orElseThrow(() -> new ProductHistoryNotFoundException("Such product history was not found"));

        return productHistoryRepository.save(productHistory);
    }

    public void deleteProductHistory(@Nonnull final Long id) {
        final var productHistoryToDelete = productHistoryRepository.findById(id).
            orElseThrow(() -> new ProductHistoryNotFoundException("Such product history was not found"));
        productHistoryRepository.delete(productHistoryToDelete);
    }
}
