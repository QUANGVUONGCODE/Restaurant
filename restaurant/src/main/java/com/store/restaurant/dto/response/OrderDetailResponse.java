package com.store.restaurant.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.store.restaurant.entity.Food;
import com.store.restaurant.entity.Order;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class OrderDetailResponse {
    Long id;

    @JsonProperty("order")
    Order order;

    @JsonProperty("food")
    Food food;


    Float price;

    Integer quantity;

    @JsonProperty("total_money")
    Float totalMoney;
}
