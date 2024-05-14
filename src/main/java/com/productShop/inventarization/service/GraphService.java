package com.productShop.inventarization.service;

import com.productShop.inventarization.model.dto.GeneralStatistic;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface GraphService {
    Map<LocalDate, Double> generateItemSellHistoryGraph(long id, String consumerKey);

    Map<LocalDate, Double> generateGeneralSellsGraph(List<GeneralStatistic> statisticList);

    Map<LocalDate, Double> generateCategorySellHistoryGraph(String category, String consumerKey);

    Map<LocalDate, Double> generateCategorySellHistoryGraph(LocalDate startDate,
                                                            LocalDate endDate,
                                                            String category,
                                                            String consumerKey);
}
