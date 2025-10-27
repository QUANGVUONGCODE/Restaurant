package com.store.restaurant.dto.request.requestUpdate;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FoodRequestUpdate {

    @Size(min = 3, max = 200, message = "NAME_SIZE")
    String name;

    @Min(value = 0, message = "PRICE_MIN")
    @Max(value = 100000000, message = "PRICE_MAX")
    Float price;

    String thumbnail;

    String description;

    @JsonProperty(value = "category_id")
    Long categoryId;

    @JsonProperty(value = "section_id")
    Long sectionId;
}
