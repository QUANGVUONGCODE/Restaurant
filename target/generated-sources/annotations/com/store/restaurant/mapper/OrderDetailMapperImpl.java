package com.store.restaurant.mapper;

import com.store.restaurant.dto.request.CartRequest;
import com.store.restaurant.dto.request.OrderDetailRequest;
import com.store.restaurant.dto.response.OrderDetailResponse;
import com.store.restaurant.entity.OrderDetail;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.8 (Oracle Corporation)"
)
@Component
public class OrderDetailMapperImpl implements OrderDetailMapper {

    @Override
    public OrderDetail mapToOrderDetail(OrderDetailRequest orderDetailRequest) {
        if ( orderDetailRequest == null ) {
            return null;
        }

        OrderDetail.OrderDetailBuilder orderDetail = OrderDetail.builder();

        if ( orderDetailRequest.getQuantity() != null ) {
            orderDetail.quantity( orderDetailRequest.getQuantity().longValue() );
        }

        return orderDetail.build();
    }

    @Override
    public OrderDetailResponse mapToOrderDetailResponse(OrderDetail orderDetail) {
        if ( orderDetail == null ) {
            return null;
        }

        OrderDetailResponse.OrderDetailResponseBuilder orderDetailResponse = OrderDetailResponse.builder();

        orderDetailResponse.id( orderDetail.getId() );
        orderDetailResponse.order( orderDetail.getOrder() );
        orderDetailResponse.food( orderDetail.getFood() );
        orderDetailResponse.price( orderDetail.getPrice() );
        if ( orderDetail.getQuantity() != null ) {
            orderDetailResponse.quantity( orderDetail.getQuantity().intValue() );
        }
        orderDetailResponse.totalMoney( orderDetail.getTotalMoney() );

        return orderDetailResponse.build();
    }

    @Override
    public OrderDetail mapToOrderDetail2(CartRequest cartRequest) {
        if ( cartRequest == null ) {
            return null;
        }

        OrderDetail.OrderDetailBuilder orderDetail = OrderDetail.builder();

        if ( cartRequest.getQuantity() != null ) {
            orderDetail.quantity( cartRequest.getQuantity().longValue() );
        }

        return orderDetail.build();
    }
}
