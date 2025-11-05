package com.store.restaurant.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PACKAGE)
public class FoodImageResponse {
    Long id;

    @JsonProperty(value = "food_id")
    Long foodId;

    @JsonProperty(value = "image_url")
    String url;
}
