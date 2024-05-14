package com.productShop.inventarization.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.productShop.inventarization.model.dto.GeneralStatistic;
import com.productShop.inventarization.model.dto.ProductHistoryProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GraphServiceImpl implements GraphService{
    private final ProductHistoryServiceImpl productHistoryService;
    private final ProductCategoryServiceImpl categoryService;

    @Override
    public Map<LocalDate, Double> generateItemSellHistoryGraph(long id, String consumerKey) {
        final var productHistories = productHistoryService.getProductHistoryByProductId(id, consumerKey);

        final var result = new TreeMap<LocalDate, Double>();

        productHistories.stream()
            .filter(productHistory -> {
                long monthsDifference = ChronoUnit.MONTHS.between(productHistory.getDate(), LocalDate.now());
                return monthsDifference <= 1;
            })
            .forEach(projection -> result.put(projection.getDate(), projection.getAmount()));

        return result;
    }

    @Override
    public Map<LocalDate, Double> generateGeneralSellsGraph(List<GeneralStatistic> statisticList) {
        final var result = new TreeMap<LocalDate, Double>();

        statisticList.stream()
                .filter(productHistory -> {
                    long monthsDifference = ChronoUnit.MONTHS.between(productHistory.getDate(), LocalDate.now());
                    return monthsDifference <= 6;
                })
                .forEach(projection -> result.put(projection.getDate(), projection.getAmount()));

        return result;
    }

    @Override
    public Map<LocalDate, Double> generateCategorySellHistoryGraph(String category, String consumerKey) {
        List<ProductHistoryProjection> productHistories;
        if (categoryService.getProductByName(category, consumerKey).isPresent()) {
            productHistories = productHistoryService.getProductHistoryByCategory(category, consumerKey);
        }
        else {
            productHistories = productHistoryService.getAllProductHistories(consumerKey);
        }

        final var result = new TreeMap<LocalDate, Double>();

        productHistories.stream()
                .filter(productHistory -> {
                    long monthsDifference = ChronoUnit.MONTHS.between(productHistory.getDate(), LocalDate.now());
                    return monthsDifference <= 6;
                })
                .forEach(projection -> result.put(projection.getDate(), projection.getAmount()));

        return result;
    }

    @Override
    public Map<LocalDate, Double> generateCategorySellHistoryGraph(LocalDate startDate,
                                                                   LocalDate endDate,
                                                                   String category,
                                                                   String consumerKey) {
        List<ProductHistoryProjection> productHistories;
        if (categoryService.getProductByName(category, consumerKey).isPresent()) {
            productHistories = productHistoryService.getProductHistoryByDateAndCategory(startDate, endDate, category, consumerKey);
        }
        else {
            productHistories = productHistoryService.getProductHistoryByDate(startDate, endDate, consumerKey);
        }

        final var result = new TreeMap<LocalDate, Double>();

        productHistories
                .forEach(projection -> result.put(projection.getDate(), projection.getAmount()));
        return result;
    }
}
