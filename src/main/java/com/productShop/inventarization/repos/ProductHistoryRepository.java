package com.productShop.inventarization.repos;

import com.productShop.inventarization.model.ProductHistory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductHistoryRepository extends JpaRepository<ProductHistory, Long> {
    List<ProductHistory> findProductHistoriesByProductId(final Long id);
}
