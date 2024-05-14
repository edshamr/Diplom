package com.productShop.inventarization.service;

import com.productShop.inventarization.model.Consumer;
import com.productShop.inventarization.model.dto.AddConsumerDto;
import com.productShop.inventarization.model.dto.GetConsumerDto;

public interface ConsumerService {
    void addConsumer(AddConsumerDto consumerDto);

    String getConsumerKey(String email);

    String getConsumer(GetConsumerDto consumerDto);

    Consumer getConsumerByEmail(String email);

    Consumer getConsumerByKey(String key);
}
