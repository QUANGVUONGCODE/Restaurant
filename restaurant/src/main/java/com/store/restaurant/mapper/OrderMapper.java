package com.store.restaurant.mapper;

import com.store.restaurant.dto.request.OrderRequest;
import com.store.restaurant.dto.request.requestUpdate.OrderUpdateRequest;
import com.store.restaurant.dto.response.OrderResponse;
import com.store.restaurant.entity.Order;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.context.annotation.Bean;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    Order mapToOrder(OrderRequest orderRequest);
    OrderResponse mapToOrderResponse(Order order);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateOrder(OrderUpdateRequest orderRequest, @MappingTarget Order order);
}
