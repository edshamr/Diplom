package com.productShop.inventarization.common.mapper;

import com.productShop.inventarization.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    void update(@MappingTarget Product productToUpdate, Product product);
}
