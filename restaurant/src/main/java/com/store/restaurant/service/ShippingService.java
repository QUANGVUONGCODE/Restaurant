package com.store.restaurant.service;

import com.store.restaurant.dto.request.ShippingRequest;
import com.store.restaurant.dto.response.ShippingResponse;
import com.store.restaurant.entity.Shipping;
import com.store.restaurant.exception.AppException;
import com.store.restaurant.exception.ErrorCode;
import com.store.restaurant.mapper.ShippingMapper;
import com.store.restaurant.repository.ShippingRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ShippingService {
    ShippingRepository shippingRepository;
    ShippingMapper shippingMapper;

    public ShippingResponse createShipping(ShippingRequest shippingRequest){
        if(shippingRepository.existsByName(shippingRequest.getName())){
            throw new AppException(ErrorCode.SHIPPING_EXISTS);
        }
        Shipping shipping = shippingMapper.mapToShipping(shippingRequest);
        return shippingMapper.toShippingResponse(shippingRepository.save(shipping));
    }

    public ShippingResponse getShippingById(Long id){
        return shippingMapper.toShippingResponse(shippingRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.INVALID_ID)));
    }

    public List<ShippingResponse> getAllShipping(){
        return shippingRepository.findAll().stream()
                .map(shippingMapper::toShippingResponse)
                .toList();
    }
    public ShippingResponse updateShipping(ShippingRequest request, Long id){
        Shipping shipping = shippingRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.INVALID_ID)
        );
        if(shippingRepository.existsByName(request.getName())){
            throw new AppException(ErrorCode.SHIPPING_EXISTS);
        }
        shippingMapper.updateShipping(request, shipping);
        return shippingMapper.toShippingResponse(shippingRepository.save(shipping));
    }

    public void deleteShipping(Long id){
        Shipping shipping = shippingRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.INVALID_ID)
        );
        shippingRepository.delete(shipping);
    }
}
