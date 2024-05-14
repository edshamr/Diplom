package com.productShop.inventarization.model.dto;

import com.productShop.inventarization.model.ProductCategory;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProductCategoryAsListDto {
    Long productId;
    List<ProductCategory> categoryList;
}
