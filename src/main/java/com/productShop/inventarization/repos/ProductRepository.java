package com.productShop.inventarization.repos;

import com.productShop.inventarization.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
