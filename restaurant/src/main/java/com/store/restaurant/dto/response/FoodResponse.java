package com.store.restaurant.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.store.restaurant.entity.Category;
import com.store.restaurant.entity.Section;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FoodResponse {
    Long id;
    String name;
    String description;
    Float price;
    String thumbnail;
    Category category;
    List<Section> sections;

    @JsonProperty(value = "created_at")
    LocalDateTime createdAt;

    @JsonProperty(value = "updated_at")
    LocalDateTime updatedAt;

    Boolean bestSeller;
    Boolean active;
}
