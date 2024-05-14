package com.productShop.inventarization.model.dto;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class GeneralStatistic {
    @Column(name = "date", nullable = false)
    LocalDate date;

    @Column(name = "amount", nullable = false)
    double amount;
}
