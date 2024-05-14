package com.productShop.inventarization.mapper;

import com.productShop.inventarization.model.ProductHistory;
import com.productShop.inventarization.model.dto.GeneralStatistic;
import com.productShop.inventarization.model.dto.ProductHistoryProjection;
import org.springframework.stereotype.Component;

@Component
public final class StatisticMapper {
    public GeneralStatistic toGeneralDayStatistic(ProductHistoryProjection productHistory) {
        return GeneralStatistic.builder()
                .date(productHistory.getDate())
                .amount(productHistory.getAmount())
                .build();
    }
}
