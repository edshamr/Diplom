package com.productShop.inventarization.repos;

import com.productShop.inventarization.model.ProductHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductHistoryRepository extends JpaRepository<ProductHistory, Long> {
    List<ProductHistory> findProductHistoriesByProductId(final Long id);
}
