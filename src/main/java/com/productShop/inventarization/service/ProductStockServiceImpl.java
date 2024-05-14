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
public class ProductStockServiceImpl implements ProductStockService{
    private final ProductStockRepository productStockRepository;

    @Override
    public List<ProductStock> getAllProductStock() {
        return productStockRepository.findAll();
    }

    @Override
    public ProductStock getProductStockById(@Nonnull final Long id, String consumerKey) {
        return productStockRepository.findByProductIdAndConsumer_Key(id, consumerKey).
            orElseThrow(() -> new ProductStockNotFoundException("Such product stock was not found"));
    }

    @Override
    public ProductStock getProductStockByProductId(@Nonnull final Long id, String consumerKey) {
        return productStockRepository.findByProductIdAndConsumer_Key(id, consumerKey).
            orElseThrow(() -> new ProductStockNotFoundException("Such product stock was not found"));
    }

    @Override
    public ProductStock saveProductStock(@Nonnull @ModelAttribute("product_stock") final ProductStock productStock) {
        if (ProductStockValidator.isProductStockValid(productStock)) {
            throw new RuntimeException("Such product stock is not valid");
        }
        return productStockRepository.save(productStock);
    }

    @Override
    public List<ProductStock> saveAllProductStock(
        @Nonnull @ModelAttribute("product_stock") final List<ProductStock> productStock) {
        return productStockRepository.saveAll(productStock);
    }

    @Override
    public ProductStock updateProductStock(@Nonnull @ModelAttribute("product_stock") final ProductStock productStock,
                                           String consumerKey) {
        if (ProductStockValidator.isProductStockValid(productStock)) {
            throw new RuntimeException("Such product stock is not valid");
        }

        ProductStock productStockToUpdate = productStockRepository.findByProductIdAndConsumer_Key(productStock.getId(), consumerKey).
                orElseThrow(() -> new ProductStockNotFoundException("Such product stock was not found"));

        productStock.setConsumer(productStockToUpdate.getConsumer());
        return productStockRepository.save(productStock);
    }

    @Override
    public void deleteProductStock(@Nonnull final Long id) {
        final var productStockToDelete = productStockRepository.findById(id).
            orElseThrow(() -> new ProductStockNotFoundException("Such product stock was not found"));
        productStockRepository.delete(productStockToDelete);
    }
}
