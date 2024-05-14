package com.productShop.inventarization.service;

import com.productShop.inventarization.exception.ProductNotFoundException;
import com.productShop.inventarization.model.Consumer;
import com.productShop.inventarization.model.Product;
import com.productShop.inventarization.model.dto.CreateProductDto;
import com.productShop.inventarization.model.dto.UpdateProductDto;
import com.productShop.inventarization.repos.ProductRepository;
import jakarta.annotation.Nonnull;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;
import java.util.Objects;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ImageSavingStrategy imageSavingStrategy;
    private final ProductCategoryServiceImpl productCategoryServiceImpl;
    private final ConsumerService consumerService;

    public ProductServiceImpl(ProductRepository productRepository,
                              ImageSavingStrategy imageSavingStrategy,
                              ProductCategoryServiceImpl productCategoryServiceImpl,
                              ConsumerService consumerService) {
        this.productRepository = productRepository;
        this.imageSavingStrategy = imageSavingStrategy;
        this.productCategoryServiceImpl = productCategoryServiceImpl;
        this.consumerService = consumerService;
    }

    @Override
    public List<Product> getAllProducts(String consumerToken) {
        return productRepository.findAllByConsumer_Key(consumerToken);
    }

    @Override
    public Product getProductById(@Nonnull final Long id, String consumerToken) {
        var product = productRepository.findByIdAndConsumer_Key(id, consumerToken).
                orElseThrow(() -> new ProductNotFoundException("Such product was not found"));
        Hibernate.initialize(product.getCategories());
        return product;
    }

    @Override
    public Product createProduct(@Nonnull @ModelAttribute("product") final CreateProductDto dto, String consumerToken) {
        Consumer consumer = consumerService.getConsumerByKey(consumerToken);

        Product product = buildProduct(dto);
        product.setConsumer(consumer);

        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(@Nonnull @ModelAttribute("product") final UpdateProductDto productDto, String consumerToken) {
        Product product = buildProduct(productDto);
        Product productToUpdate = productRepository.findByIdAndConsumer_Key(product.getId(), consumerToken).
                orElseThrow(() -> new ProductNotFoundException("Such product was not found"));
        product.setConsumer(productToUpdate.getConsumer());
        return productRepository.save(updateProduct(productToUpdate, product));
    }

    @Override
    public void deleteProduct(@Nonnull final Long id, final String consumerToken) {
        final var productToDelete = productRepository.findByIdAndConsumer_Key(id, consumerToken).
                orElseThrow(() -> new ProductNotFoundException("Such product was not found"));
        productRepository.delete(productToDelete);
    }

    private Product buildProduct(CreateProductDto dto) {
        return Product.builder()
                .name(dto.getName())
                .vendorCode(dto.getVendorCode())
                .categories(dto.getCategories())
                .price(dto.getPrice())
                .image(imageSavingStrategy.saveImage(dto.getImage()))
                .build();
    }

    private Product buildProduct(UpdateProductDto dto) {
        return Product.builder()
                .id(dto.getId())
                .name(dto.getName())
                .vendorCode(dto.getVendorCode())
                .categories(productCategoryServiceImpl.getProductCategoriesById(dto.getCategories()))
                .price(dto.getPrice())
                .image((dto.getName() == null || dto.getImage().isEmpty())
                        ? null
                        : imageSavingStrategy.saveImage(dto.getImage()))
                .build();
    }

    private Product updateProduct(Product productToUpdate, Product product) {
        productToUpdate.setName(
                Objects.isNull(product.getName()) ? productToUpdate.getName() : product.getName());
        productToUpdate.setVendorCode(
                Objects.isNull(product.getVendorCode()) ? productToUpdate.getVendorCode() : product.getVendorCode());
        productToUpdate.setPrice(
                Objects.isNull(product.getPrice()) ? productToUpdate.getPrice() : product.getPrice());
        productToUpdate.setImage(
                Objects.isNull(product.getImage()) ? productToUpdate.getImage() : product.getImage());
        productToUpdate.setCategories(
                Objects.isNull(product.getCategories()) ? productToUpdate.getCategories() : product.getCategories());
        return productToUpdate;
    }
}
