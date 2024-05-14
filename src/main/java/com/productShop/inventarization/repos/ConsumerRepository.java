package com.productShop.inventarization.repos;

import com.productShop.inventarization.model.Consumer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConsumerRepository extends JpaRepository<Consumer, Long> {

    Optional<Consumer> findByEmail(String email);
    Optional<Consumer> findByKey(String email);
    Optional<Consumer> findByUsername(String username);
}
