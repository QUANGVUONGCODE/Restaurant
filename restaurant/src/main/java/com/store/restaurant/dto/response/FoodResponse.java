package com.store.restaurant.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

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
    @JsonProperty(value = "category_id")
    Long categoryId;

    @JsonProperty(value = "section_id")
    Long sectionId;

    @JsonProperty(value = "created_at")
    LocalDateTime createdAt;

    @JsonProperty(value = "updated_at")
    LocalDateTime updatedAt;
}
