package com.store.restaurant.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.store.restaurant.enums.TableStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TableRequest {
    String name;

    Integer capacity;

    @JsonProperty("table_status")
    TableStatus status;
}
