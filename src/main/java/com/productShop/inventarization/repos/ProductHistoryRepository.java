package com.productShop.inventarization.repos;

import com.productShop.inventarization.model.dto.ProductHistoryProjection;
import com.productShop.inventarization.model.ProductHistory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductHistoryRepository extends JpaRepository<ProductHistory, Long> {
    //    List<ProductHistory> findProductHistoriesByProductId(final Long id);
    @Query("""
        SELECT new com.productShop.inventarization.model.dto.ProductHistoryProjection(ph.date, SUM(ph.amount))
        FROM ProductHistory ph
        WHERE ph.product.id = :id
        GROUP BY ph.date
        order by ph.date
        """)
    List<ProductHistoryProjection> findProductHistoriesByProductId(final Long id);
}
