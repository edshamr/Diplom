package com.productShop.inventarization.service;

import com.productShop.inventarization.exception.ProductCategoryNotFoundException;
import com.productShop.inventarization.exception.ProductNotFoundException;
import com.productShop.inventarization.model.Product;
import com.productShop.inventarization.model.ProductCategory;
import com.productShop.inventarization.repos.ProductCategoryRepository;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductCategoryService {
    private final ProductCategoryRepository productCategoryRepository;

    public List<ProductCategory> getAllProductCategories() {
        return productCategoryRepository.findAll();
    }

    public ProductCategory getProductById(@Nonnull final Long id) {
        return productCategoryRepository.findById(id).
                orElseThrow(() -> new ProductCategoryNotFoundException("Such product category was not found"));
    }

    public ProductCategory createProductCategory(@Nonnull @ModelAttribute("product_category") final ProductCategory productCategory) {
        return productCategoryRepository.save(productCategory);
    }

    public ProductCategory updateProductCategory(@Nonnull @ModelAttribute("product_category") final ProductCategory productCategory) {
        productCategoryRepository.findById(productCategory.getId()).
                orElseThrow(() -> new ProductCategoryNotFoundException("Such product category was not found"));
        return productCategoryRepository.save(productCategory);
    }

    public void deleteProductCategory(@Nonnull final Long id) {
        final var productCategoryToDelete = productCategoryRepository.findById(id).
                orElseThrow(() -> new ProductCategoryNotFoundException("Such product category was not found"));
        productCategoryRepository.delete(productCategoryToDelete);
    }
}
