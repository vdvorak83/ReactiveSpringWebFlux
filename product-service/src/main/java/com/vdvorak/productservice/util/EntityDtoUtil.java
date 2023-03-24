package com.vdvorak.productservice.util;

import com.vdvorak.productservice.dto.ProductDto;
import com.vdvorak.productservice.model.ProductEntity;
import org.springframework.beans.BeanUtils;

public class EntityDtoUtil {
    public static ProductDto toDto(ProductEntity productEntity){
        ProductDto dto = new ProductDto();
        BeanUtils.copyProperties(productEntity, dto);
        return dto;
    }

    public static ProductEntity toEntity(ProductDto productDto){
        ProductEntity product = new ProductEntity();
        BeanUtils.copyProperties(productDto, product);
        return product;
    }
}
