package com.productShop.inventarization.service;

import com.productShop.inventarization.mapper.StatisticMapper;
import com.productShop.inventarization.model.dto.GeneralStatistic;
import com.productShop.inventarization.model.dto.ProductHistoryProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticServiceImpl implements StatisticService {
    private final ProductHistoryServiceImpl productHistoryService;
    private final StatisticMapper statisticMapper;

    public List<GeneralStatistic> getGeneralStatistics(String consumerKey) {
        List<ProductHistoryProjection> allProductHistories =
                productHistoryService.getAllProductHistories(consumerKey);

        return allProductHistories.stream()
                .map(statisticMapper::toGeneralDayStatistic)
                .toList();
    }
}
