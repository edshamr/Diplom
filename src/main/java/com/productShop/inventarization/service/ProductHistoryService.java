package com.productShop.inventarization.service;

import com.productShop.inventarization.model.ProductHistory;
import com.productShop.inventarization.model.dto.ProductHistoryProjection;
import com.productShop.inventarization.model.dto.ProductHistorySupplyProjection;
import jakarta.annotation.Nonnull;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.time.LocalDate;
import java.util.List;

public interface ProductHistoryService {
    List<ProductHistoryProjection> getAllProductHistories(String consumerKey);

    List<ProductHistorySupplyProjection> getAllProductSupplyHistories(String consumerKey);

    List<ProductHistorySupplyProjection> getAllProductSupplyHistoriesAndDate(String consumerKey,
                                                                             LocalDate startDate,
                                                                             LocalDate endDate);

    List<ProductHistoryProjection> getProductHistoryByProductId(@Nonnull final Long id,
                                                                @Nonnull final String consumerKey);

    List<ProductHistoryProjection> getProductHistoryByCategory(@Nonnull final String category,
                                                               @Nonnull final String consumerKey);

    ProductHistory createProductHistory(
            @Nonnull @ModelAttribute("product_history") final ProductHistory productHistory);

    ProductHistory updateProductHistory(
            @Nonnull @ModelAttribute("product_history") final ProductHistory productHistory);

    void deleteProductHistory(@Nonnull final Long id);

    List<ProductHistoryProjection> getProductHistoryByDateAndCategory(LocalDate startDate,
                                                                      LocalDate endDate,
                                                                      String category,
                                                                      String consumerKey);

    List<ProductHistoryProjection> getProductHistoryByDate(LocalDate startDate,
                                                           LocalDate endDate,
                                                           String consumerKey);
}
