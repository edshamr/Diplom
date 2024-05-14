package com.productShop.inventarization.repos;

import com.productShop.inventarization.model.ProductHistory;
import com.productShop.inventarization.model.dto.ProductHistoryProjection;
import com.productShop.inventarization.model.dto.ProductHistorySupplyProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProductHistoryRepository extends JpaRepository<ProductHistory, Long> {
    @Query("""
            SELECT new com.productShop.inventarization.model.dto.ProductHistoryProjection(ph.date, SUM(ph.amount))
            FROM ProductHistory ph
            JOIN ph.product p
            WHERE p.id = :id AND ph.consumer.key = :consumerKey
            GROUP BY ph.date
            order by ph.date
            """)
    List<ProductHistoryProjection> findProductHistoriesByProductId(final Long id, final String consumerKey);

    @Query("""
            SELECT new com.productShop.inventarization.model.dto.ProductHistoryProjection(ph.date, SUM(ph.amount))
            FROM ProductHistory ph
            JOIN ph.product p
            JOIN p.categories c
            WHERE :category = c.name AND c.consumer.key = :consumerKey
            GROUP BY ph.date
            order by ph.date
            """)
    List<ProductHistoryProjection> findProductHistoryByCategory(final String category, final String consumerKey);

    @Query("""
            SELECT new com.productShop.inventarization.model.dto.ProductHistoryProjection(ph.date, ph.amount)
            FROM ProductHistory ph
            JOIN ph.product p
            JOIN p.categories c
            WHERE p.consumer.key = :consumerKey
            order by ph.date
            """)
    List<ProductHistoryProjection> findAllByConsumerKey(final String consumerKey);

    @Query("""
            SELECT new com.productShop.inventarization.model.dto.ProductHistorySupplyProjection(p.name, ph.date, ph.amount, ph.operation)
            FROM ProductHistory ph
            JOIN ph.product p
            WHERE ph.consumer.key = :consumerKey
            order by ph.date
            """)
    List<ProductHistorySupplyProjection> findAllSupplyByConsumerKey(final String consumerKey);

    @Query("""
            SELECT new com.productShop.inventarization.model.dto.ProductHistorySupplyProjection(p.name, ph.date, ph.amount, ph.operation)
            FROM ProductHistory ph
            JOIN ph.product p
            WHERE ph.consumer.key = :consumerKey AND ph.date BETWEEN :startDate AND :endDate
            order by ph.date
            """)
    List<ProductHistorySupplyProjection> findAllSupplyProjectionByConsumerKeyAndDate(final String consumerKey,
                                                                                     final LocalDate startDate,
                                                                                     final LocalDate endDate);

    @Query("""
            SELECT new com.productShop.inventarization.model.dto.ProductHistoryProjection(ph.date, ph.amount)
            FROM ProductHistory ph
            JOIN ph.product p
            JOIN p.categories c
            WHERE ph.consumer.key = :consumerKey AND ph.date BETWEEN :startDate AND :endDate AND :category = c.name
            order by ph.date
            """)
    List<ProductHistoryProjection> findAllSupplyByConsumerKeyAndDateAndCategory(final String consumerKey,
                                                                                final LocalDate startDate,
                                                                                final LocalDate endDate,
                                                                                final String category);

    @Query("""
            SELECT new com.productShop.inventarization.model.dto.ProductHistoryProjection(ph.date, ph.amount)
            FROM ProductHistory ph
            JOIN ph.product p
            WHERE ph.consumer.key = :consumerKey AND ph.date BETWEEN :startDate AND :endDate
            order by ph.date
            """)
    List<ProductHistoryProjection> findAllSupplyByConsumerKeyAndDate(final String consumerKey,
                                                                     final LocalDate startDate,
                                                                     final LocalDate endDate);
}
