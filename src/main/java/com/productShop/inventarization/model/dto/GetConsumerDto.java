package com.productShop.inventarization.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetConsumerDto {
    String username;
    String password;
    String email;

}
