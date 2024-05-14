package com.productShop.inventarization.model.dto;

import com.productShop.inventarization.model.Consumer;
import com.productShop.inventarization.model.ProductCategory;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@Data
public class CreateProductDto {
    String vendorCode;
    String name;
    MultipartFile image;
    Set<ProductCategory> categories;
    double price;
}
