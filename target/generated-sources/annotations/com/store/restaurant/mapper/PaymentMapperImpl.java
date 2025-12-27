package com.store.restaurant.mapper;

import com.store.restaurant.dto.request.PaymentRequest;
import com.store.restaurant.dto.response.PaymentResponse;
import com.store.restaurant.entity.Payment;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.8 (Oracle Corporation)"
)
@Component
public class PaymentMapperImpl implements PaymentMapper {

    @Override
    public Payment mapToPayment(PaymentRequest request) {
        if ( request == null ) {
            return null;
        }

        Payment.PaymentBuilder payment = Payment.builder();

        payment.name( request.getName() );
        payment.description( request.getDescription() );

        return payment.build();
    }

    @Override
    public PaymentResponse mapToPaymentResponse(Payment payment) {
        if ( payment == null ) {
            return null;
        }

        PaymentResponse.PaymentResponseBuilder paymentResponse = PaymentResponse.builder();

        paymentResponse.id( payment.getId() );
        paymentResponse.name( payment.getName() );
        paymentResponse.description( payment.getDescription() );

        return paymentResponse.build();
    }

    @Override
    public void updatePayment(Payment payment, PaymentRequest request) {
        if ( payment == null ) {
            return;
        }

        request.setName( payment.getName() );
        request.setDescription( payment.getDescription() );
    }
}
