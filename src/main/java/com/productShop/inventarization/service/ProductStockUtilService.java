package com.productShop.inventarization.service;

import com.productShop.inventarization.model.ProductStock;
import com.productShop.inventarization.model.SupplyOrder;
import com.productShop.inventarization.model.dto.SupplyDto;

public interface ProductStockUtilService {
    SupplyOrder createSupply(SupplyDto supplyDTO, String consumerKey);

    ProductStock sellProduct(long productStockId, double sellAmount, String consumerKey);

    ProductStock writeOffProductAmount(long productStockId, double writeOffAmount, String consumerKey);

    ProductStock addAmountToProduct(long productStockId, double toAddAmount, String consumerKey);
}
