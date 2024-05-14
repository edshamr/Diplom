package com.productShop.inventarization.service;

import com.productShop.inventarization.model.ProductCategory;
import com.productShop.inventarization.model.dto.AddCategoryDto;
import jakarta.annotation.Nonnull;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ProductCategoryService {
    List<ProductCategory> getAllProductCategories(@Nonnull final String consumerKey);

    ProductCategory getProductById(@Nonnull final Long id);

    Optional<ProductCategory> getProductByName(@Nonnull final String name, @Nonnull String consumerKey);

    ProductCategory createProductCategory(
            @Nonnull @ModelAttribute("product_category") final AddCategoryDto categoryDto,
            @Nonnull final String consumerKey);

    ProductCategory createProductCategory(
            @Nonnull @ModelAttribute("product_category") final ProductCategory productCategory,
            @Nonnull final String consumerKey);

    ProductCategory updateProductCategory(
            @Nonnull @ModelAttribute("product_category") final ProductCategory productCategory);

    void deleteProductCategory(@Nonnull final Long id);

    Set<ProductCategory> getProductCategoriesById(Set<Long> categoryIds);
}
