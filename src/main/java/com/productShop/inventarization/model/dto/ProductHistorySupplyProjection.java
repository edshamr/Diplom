package com.productShop.inventarization.model.dto;

import com.productShop.inventarization.model.OperationType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class ProductHistorySupplyProjection {
    String name;

    LocalDate date;

    double amount;

    OperationType operationType;
}
