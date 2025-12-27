package com.store.restaurant.mapper;

import com.store.restaurant.dto.request.CartRequest;
import com.store.restaurant.dto.request.OrderDetailRequest;
import com.store.restaurant.dto.response.OrderDetailResponse;
import com.store.restaurant.entity.OrderDetail;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderDetailMapper {
    OrderDetail mapToOrderDetail(OrderDetailRequest orderDetailRequest);
    OrderDetailResponse mapToOrderDetailResponse(OrderDetail orderDetail);
    OrderDetail mapToOrderDetail2(CartRequest cartRequest);
}
