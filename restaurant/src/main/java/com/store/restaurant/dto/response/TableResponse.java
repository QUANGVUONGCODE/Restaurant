package com.store.restaurant.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TableResponse {
    Long id;

    @JsonProperty("table_name")
    String name;

    @JsonProperty("capacity")
    Integer capacity;

    @JsonProperty("table_status")
    String status;
}
