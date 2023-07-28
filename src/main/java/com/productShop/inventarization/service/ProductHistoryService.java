package com.productShop.inventarization.service;

import com.productShop.inventarization.exception.ProductHistoryNotFoundException;
import com.productShop.inventarization.model.ProductHistory;
import com.productShop.inventarization.repos.ProductHistoryRepository;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

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

    public ProductHistory createProductHistory(@Nonnull @ModelAttribute("product_history") final ProductHistory productHistory) {
        return productHistoryRepository.save(productHistory);
    }

    public ProductHistory updateProductHistory(@Nonnull @ModelAttribute("product_history") final ProductHistory productHistory) {
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
