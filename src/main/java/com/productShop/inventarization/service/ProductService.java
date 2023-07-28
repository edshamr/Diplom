package com.productShop.inventarization.service;

import com.productShop.inventarization.model.Product;
import com.productShop.inventarization.repos.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    public List<Product> getAllProducts() {

        return List.of(
                new Product(1L, "123", "name1", "image1"),
                new Product(2L, "456", "name2", "image2"),
                new Product(3L, "789", "name3", "image3")
        );
    }
}
