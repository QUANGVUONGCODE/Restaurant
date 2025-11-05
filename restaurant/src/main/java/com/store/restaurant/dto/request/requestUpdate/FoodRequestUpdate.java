package com.store.restaurant.dto.request.requestUpdate;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FoodRequestUpdate {

    @NotBlank(message = "NOT_BLANK_NAME")
    @Size(min = 3, max = 200, message = "NAME_SIZE")
    String name;

    @Min(value = 0, message = "PRICE_MIN")
    @Max(value = 100000000, message = "PRICE_MAX")
    Float price;

    String thumbnail;

    String description;

    @JsonProperty(value = "category_id", required = true)
    Long categoryId;

    @JsonProperty(value = "section_ids", required = true)
    List<Long> sectionIds;

    Boolean bestSeller;
    Boolean active;
}
