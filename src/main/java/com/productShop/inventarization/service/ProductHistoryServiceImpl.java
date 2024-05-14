package com.productShop.inventarization.service;

import com.productShop.inventarization.common.validator.ProductHistoryValidator;
import com.productShop.inventarization.exception.ProductHistoryNotFoundException;
import com.productShop.inventarization.model.ProductHistory;
import com.productShop.inventarization.model.dto.ProductHistoryProjection;
import com.productShop.inventarization.model.dto.ProductHistorySupplyProjection;
import com.productShop.inventarization.repos.ProductHistoryRepository;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductHistoryServiceImpl implements ProductHistoryService {
    private final ProductHistoryRepository productHistoryRepository;

    @Override
    public List<ProductHistoryProjection> getAllProductHistories(String consumerKey) {
        return productHistoryRepository.findAllByConsumerKey(consumerKey);
    }

    @Override
    public List<ProductHistorySupplyProjection> getAllProductSupplyHistories(String consumerKey) {
        return productHistoryRepository.findAllSupplyByConsumerKey(consumerKey);
    }

    @Override
    public List<ProductHistorySupplyProjection> getAllProductSupplyHistoriesAndDate(String consumerKey,
                                                                                    LocalDate startDate,
                                                                                    LocalDate endDate) {
        return productHistoryRepository.findAllSupplyProjectionByConsumerKeyAndDate(consumerKey, startDate, endDate);
    }

    @Override
    public List<ProductHistoryProjection> getProductHistoryByProductId(@Nonnull final Long id,
                                                                       @Nonnull final String consumerKey) {
        return productHistoryRepository.findProductHistoriesByProductId(id, consumerKey);
    }

    @Override
    public List<ProductHistoryProjection> getProductHistoryByCategory(@Nonnull final String category,
                                                                      @Nonnull final String consumerKey) {
        return productHistoryRepository.findProductHistoryByCategory(category, consumerKey);
    }

    @Override
    public ProductHistory createProductHistory(
            @Nonnull @ModelAttribute("product_history") final ProductHistory productHistory) {

        if (ProductHistoryValidator.isProductHistoryValid(productHistory)) {
            throw new RuntimeException("Such product history is not valid.");
        }
        return productHistoryRepository.save(productHistory);
    }

    @Override
    public ProductHistory updateProductHistory(
            @Nonnull @ModelAttribute("product_history") final ProductHistory productHistory) {
        if (ProductHistoryValidator.isProductHistoryValid(productHistory)) {
            throw new RuntimeException("Such product history is not valid.");
        }

        productHistoryRepository.findById(productHistory.getId()).
                orElseThrow(() -> new ProductHistoryNotFoundException("Such product history was not found"));

        return productHistoryRepository.save(productHistory);
    }

    @Override
    public void deleteProductHistory(@Nonnull final Long id) {
        final var productHistoryToDelete = productHistoryRepository.findById(id).
                orElseThrow(() -> new ProductHistoryNotFoundException("Such product history was not found"));
        productHistoryRepository.delete(productHistoryToDelete);
    }

    @Override
    public List<ProductHistoryProjection> getProductHistoryByDateAndCategory(LocalDate startDate,
                                                                             LocalDate endDate,
                                                                             String category,
                                                                             String consumerKey) {
        return productHistoryRepository.findAllSupplyByConsumerKeyAndDateAndCategory(consumerKey, startDate, endDate, category);
    }

    @Override
    public List<ProductHistoryProjection> getProductHistoryByDate(LocalDate startDate,
                                                                  LocalDate endDate,
                                                                  String consumerKey) {
        return productHistoryRepository.findAllSupplyByConsumerKeyAndDate(consumerKey, startDate, endDate);
    }
}
