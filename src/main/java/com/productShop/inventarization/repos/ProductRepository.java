package com.productShop.inventarization.repos;

import com.productShop.inventarization.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByConsumer_Key(String key);

    Optional<Product> findByIdAndConsumer_Key(Long id, String key);

}
