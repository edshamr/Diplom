package com.productShop.inventarization.repos;

import com.productShop.inventarization.model.ProductCategory;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
    Optional<ProductCategory> findFirstByName(String name);
    List<ProductCategory> findAllByConsumer_Key(String key);
    Optional<ProductCategory>  findByNameAndConsumer_Key(String name, String consumerKey);
}
