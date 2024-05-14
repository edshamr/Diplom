package com.productShop.inventarization.repos;

import com.productShop.inventarization.model.ProductStock;
import com.productShop.inventarization.model.SupplyOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SupplyOrderRepository extends JpaRepository<SupplyOrder, Long> {
    Optional<SupplyOrder> findByIdAndConsumer_Key(long id, String key);
    List<SupplyOrder> findAllByAndConsumer_Key(String key);
}
