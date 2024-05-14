package com.productShop.inventarization.model.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@Data
@Builder
public class UpdateProductDto {
    private Long id;
    private String vendorCode;
    private String name;
    private MultipartFile image;
    private Double price;
    private Set<Long> categories;
}
