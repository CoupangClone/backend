package com.blanc.market.domain.order.mapper;

import com.blanc.market.domain.order.dto.OrderProductResponse;
import com.blanc.market.domain.order.entity.OrderProduct;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderProductMapper {

    @Mapping(source = "product.id", target = "productId")
    OrderProductResponse toDto(OrderProduct entity);
}
