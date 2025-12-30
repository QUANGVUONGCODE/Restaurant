package com.store.restaurant.mapper;

import com.store.restaurant.dto.request.OrderRequest;
import com.store.restaurant.dto.request.requestUpdate.OrderUpdateRequest;
import com.store.restaurant.dto.response.OrderResponse;
import com.store.restaurant.entity.Order;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.8 (Oracle Corporation)"
)
@Component
public class OrderMapperImpl implements OrderMapper {

    @Override
    public Order mapToOrder(OrderRequest orderRequest) {
        if ( orderRequest == null ) {
            return null;
        }

        Order.OrderBuilder order = Order.builder();

        order.fullName( orderRequest.getFullName() );
        order.email( orderRequest.getEmail() );
        order.phoneNumber( orderRequest.getPhoneNumber() );
        order.numberOfGuest( orderRequest.getNumberOfGuest() );
        order.note( orderRequest.getNote() );
        order.orderDate( orderRequest.getOrderDate() );
        order.endTime( orderRequest.getEndTime() );
        order.vnpTxnRef( orderRequest.getVnpTxnRef() );

        return order.build();
    }

    @Override
    public OrderResponse mapToOrderResponse(Order order) {
        if ( order == null ) {
            return null;
        }

        OrderResponse.OrderResponseBuilder orderResponse = OrderResponse.builder();

        orderResponse.id( order.getId() );
        orderResponse.orderCode( order.getOrderCode() );
        orderResponse.user( order.getUser() );
        orderResponse.fullName( order.getFullName() );
        orderResponse.email( order.getEmail() );
        orderResponse.phoneNumber( order.getPhoneNumber() );
        orderResponse.numberOfGuest( order.getNumberOfGuest() );
        orderResponse.table( order.getTable() );
        orderResponse.note( order.getNote() );
        orderResponse.orderDate( order.getOrderDate() );
        orderResponse.endTime( order.getEndTime() );
        orderResponse.status( order.getStatus() );
        orderResponse.totalMoney( order.getTotalMoney() );
        orderResponse.active( order.getActive() );
        orderResponse.payment( order.getPayment() );
        orderResponse.vnpTxnRef( order.getVnpTxnRef() );

        return orderResponse.build();
    }

    @Override
    public void updateOrder(OrderUpdateRequest orderRequest, Order order) {
        if ( orderRequest == null ) {
            return;
        }

        if ( orderRequest.getFullName() != null ) {
            order.setFullName( orderRequest.getFullName() );
        }
        if ( orderRequest.getEmail() != null ) {
            order.setEmail( orderRequest.getEmail() );
        }
        if ( orderRequest.getPhoneNumber() != null ) {
            order.setPhoneNumber( orderRequest.getPhoneNumber() );
        }
        if ( orderRequest.getNumberOfGuest() != null ) {
            order.setNumberOfGuest( orderRequest.getNumberOfGuest().intValue() );
        }
        if ( orderRequest.getNote() != null ) {
            order.setNote( orderRequest.getNote() );
        }
        if ( orderRequest.getStatus() != null ) {
            order.setStatus( orderRequest.getStatus().name() );
        }
        if ( orderRequest.getVnpTxnRef() != null ) {
            order.setVnpTxnRef( orderRequest.getVnpTxnRef() );
        }
    }
}
