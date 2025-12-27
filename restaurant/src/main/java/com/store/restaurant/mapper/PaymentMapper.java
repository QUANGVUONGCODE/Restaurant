package com.store.restaurant.mapper;

import com.store.restaurant.dto.request.PaymentRequest;
import com.store.restaurant.dto.response.PaymentResponse;
import com.store.restaurant.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    Payment mapToPayment(PaymentRequest request);
    PaymentResponse mapToPaymentResponse(Payment payment);
    void updatePayment(Payment payment, @MappingTarget PaymentRequest request);
}
