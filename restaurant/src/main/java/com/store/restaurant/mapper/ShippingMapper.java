package com.store.restaurant.mapper;

import com.store.restaurant.dto.request.ShippingRequest;
import com.store.restaurant.dto.response.ShippingResponse;
import com.store.restaurant.entity.Shipping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ShippingMapper {
    Shipping mapToShipping(ShippingRequest shippingRequest);
    ShippingResponse toShippingResponse(Shipping shipping);
    void updateShipping(ShippingRequest shippingRequest, @MappingTarget Shipping shipping);
}

