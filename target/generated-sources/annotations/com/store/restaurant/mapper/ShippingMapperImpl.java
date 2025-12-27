package com.store.restaurant.mapper;

import com.store.restaurant.dto.request.ShippingRequest;
import com.store.restaurant.dto.response.ShippingResponse;
import com.store.restaurant.entity.Shipping;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.8 (Oracle Corporation)"
)
@Component
public class ShippingMapperImpl implements ShippingMapper {

    @Override
    public Shipping mapToShipping(ShippingRequest shippingRequest) {
        if ( shippingRequest == null ) {
            return null;
        }

        Shipping.ShippingBuilder shipping = Shipping.builder();

        shipping.name( shippingRequest.getName() );
        shipping.description( shippingRequest.getDescription() );

        return shipping.build();
    }

    @Override
    public ShippingResponse toShippingResponse(Shipping shipping) {
        if ( shipping == null ) {
            return null;
        }

        ShippingResponse.ShippingResponseBuilder shippingResponse = ShippingResponse.builder();

        shippingResponse.id( shipping.getId() );
        shippingResponse.name( shipping.getName() );
        shippingResponse.description( shipping.getDescription() );

        return shippingResponse.build();
    }

    @Override
    public void updateShipping(ShippingRequest shippingRequest, Shipping shipping) {
        if ( shippingRequest == null ) {
            return;
        }

        shipping.setName( shippingRequest.getName() );
        shipping.setDescription( shippingRequest.getDescription() );
    }
}
