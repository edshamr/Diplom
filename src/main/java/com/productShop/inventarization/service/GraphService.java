package com.productShop.inventarization.service;

import com.productShop.inventarization.model.ProductHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;

import java.time.LocalDate;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GraphService {
    private final ProductHistoryService productHistoryService;

    public Map<LocalDate, Double> generateItemSellHistoryGraph(long id) {
        final var productHistories = productHistoryService.getProductHistoryByProductId(id);

        final var graphData = productHistories.stream()
                .filter(productHistory -> {
                    long monthsDifference = ChronoUnit.MONTHS.between(productHistory.getDate(), LocalDate.now());
                    return monthsDifference <= 1;
                })
                .collect(Collectors.toMap(
                        ProductHistory::getDate,
                        ProductHistory::getAmount
                ));

        return graphData;
    }
}
