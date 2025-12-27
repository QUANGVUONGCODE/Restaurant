package com.store.restaurant.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDetailRequest {
    @JsonProperty("order_id")
    Long orderId;

    @JsonProperty("order_id")
    Long foodId;

    Integer quantity;
}
