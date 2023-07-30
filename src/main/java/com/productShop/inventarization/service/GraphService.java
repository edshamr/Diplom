package com.productShop.inventarization.service;

import com.productShop.inventarization.model.ProductHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
//        Map<String, Integer> graphData = new TreeMap<>();
//        graphData.put("2016", 147);
//        graphData.put("2017", 1256);
//        graphData.put("2018", 3856);
//        graphData.put("2019", 19807);

        final var graphData = productHistories.stream()
                .collect(Collectors.toMap(
                        ProductHistory::getDate,
                        ProductHistory::getAmount
                ));

        return graphData;
    }
}
