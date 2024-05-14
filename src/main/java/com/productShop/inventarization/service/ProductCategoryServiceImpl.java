package com.productShop.inventarization.service;

import com.productShop.inventarization.exception.ProductCategoryNotFoundException;
import com.productShop.inventarization.model.Consumer;
import com.productShop.inventarization.model.ProductCategory;
import com.productShop.inventarization.model.dto.AddCategoryDto;
import com.productShop.inventarization.repos.ProductCategoryRepository;
import jakarta.annotation.Nonnull;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

@Service
@RequiredArgsConstructor
public class ProductCategoryServiceImpl implements ProductCategoryService{
    private final ProductCategoryRepository productCategoryRepository;
    private final ConsumerService consumerService;

    @Override
    public List<ProductCategory> getAllProductCategories(@Nonnull final String consumerKey) {
        return productCategoryRepository.findAllByConsumer_Key(consumerKey);
    }

    @Override
    public ProductCategory getProductById(@Nonnull final Long id) {
        return productCategoryRepository.findById(id).
            orElseThrow(() -> new ProductCategoryNotFoundException("Such product category was not found"));
    }

    @Override
    public Optional<ProductCategory> getProductByName(@Nonnull final String name, @Nonnull String consumerKey) {
        return productCategoryRepository.findByNameAndConsumer_Key(name, consumerKey);
    }

    @Override
    public ProductCategory createProductCategory(
            @Nonnull @ModelAttribute("product_category") final AddCategoryDto categoryDto,
            @Nonnull final String consumerKey) {
        Consumer consumer = consumerService.getConsumerByKey(consumerKey);

        ProductCategory categoryToSave = new ProductCategory().builder()
                .name(categoryDto.getName())
                .consumer(consumer)
                .build();
        return productCategoryRepository.save(categoryToSave);
    }

    @Override
    public ProductCategory createProductCategory(
        @Nonnull @ModelAttribute("product_category") final ProductCategory productCategory,
        @Nonnull final String consumerKey) {
        Consumer consumer = consumerService.getConsumerByKey(consumerKey);

        productCategory.setConsumer(consumer);
        return productCategoryRepository.save(productCategory);
    }

    @Override
    public ProductCategory updateProductCategory(
        @Nonnull @ModelAttribute("product_category") final ProductCategory productCategory) {
        productCategoryRepository.findById(productCategory.getId()).
            orElseThrow(() -> new ProductCategoryNotFoundException("Such product category was not found"));
        return productCategoryRepository.save(productCategory);
    }

    @Override
    public void deleteProductCategory(@Nonnull final Long id) {
        final var productCategoryToDelete = productCategoryRepository.findById(id).
            orElseThrow(() -> new ProductCategoryNotFoundException("Such product category was not found"));
        productCategoryRepository.delete(productCategoryToDelete);
    }

    @Override
    public Set<ProductCategory> getProductCategoriesById(Set<Long> categoryIds) {
        Set<ProductCategory> categories = new HashSet<>();
        if (categoryIds != null && !categoryIds.isEmpty()) {
            categories.addAll(productCategoryRepository.findAllById(categoryIds));
        }
        return categories;
    }
}
