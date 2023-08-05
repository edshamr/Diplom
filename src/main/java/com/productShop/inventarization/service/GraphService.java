package com.productShop.inventarization.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.TreeMap;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GraphService {
    private final ProductHistoryService productHistoryService;

    public Map<LocalDate, Double> generateItemSellHistoryGraph(long id) {
        final var productHistories = productHistoryService.getProductHistoryByProductId(id);

        final var result = new TreeMap<LocalDate, Double>();

        productHistories.stream()
            .filter(productHistory -> {
                long monthsDifference = ChronoUnit.MONTHS.between(productHistory.getDate(), LocalDate.now());
                return monthsDifference <= 1;
            })
            .forEach(projection -> result.put(projection.getDate(), projection.getAmount()));

        return result;
    }
}
