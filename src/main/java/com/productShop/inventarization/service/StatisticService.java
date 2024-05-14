package com.productShop.inventarization.service;

import com.productShop.inventarization.model.dto.GeneralStatistic;

import java.util.List;

public interface StatisticService {
    List<GeneralStatistic> getGeneralStatistics(String consumerKey);
}
