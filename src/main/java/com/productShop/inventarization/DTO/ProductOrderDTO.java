package com.productShop.inventarization.DTO;

import com.productShop.inventarization.model.ProductStock;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class ProductOrderDTO {
    ProductStock productStock;
    double orderAmount;
}