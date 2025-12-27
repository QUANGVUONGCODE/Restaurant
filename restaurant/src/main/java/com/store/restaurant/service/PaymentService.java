package com.store.restaurant.service;

import com.store.restaurant.dto.request.PaymentRequest;
import com.store.restaurant.dto.response.PaymentResponse;
import com.store.restaurant.entity.Payment;
import com.store.restaurant.exception.AppException;
import com.store.restaurant.exception.ErrorCode;
import com.store.restaurant.mapper.PaymentMapper;
import com.store.restaurant.repository.PaymentRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentService {
    PaymentRepository paymentRepository;
    PaymentMapper paymentMapper;

    public PaymentResponse createPayment(PaymentRequest paymentRequest){
        if(paymentRepository.existsByName(paymentRequest.getName())){
            throw new AppException(ErrorCode.PAYMENT_EXISTS);
        }
        Payment payment = paymentMapper.mapToPayment(paymentRequest);
        return paymentMapper.mapToPaymentResponse(paymentRepository.save(payment));
    }

    public List<PaymentResponse> getAllPayments(){
        return paymentRepository.findAll().stream().map(paymentMapper::mapToPaymentResponse).toList();
    }

    public PaymentResponse getPaymentById(Long id){
        return paymentMapper.mapToPaymentResponse(paymentRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.INVALID_ID)
        ));
    }

    public PaymentResponse updatePayment(Long id, PaymentRequest paymentRequest){
        Payment payment = paymentRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.INVALID_ID)
        );
        paymentMapper.updatePayment(payment, paymentRequest);
        return paymentMapper.mapToPaymentResponse(paymentRepository.save(payment));
    }

    public void deletePayment(Long id){
        if(!paymentRepository.existsById(id)){
            throw new AppException(ErrorCode.INVALID_ID);
        }
        paymentRepository.deleteById(id);
    }
}
