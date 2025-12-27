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
public class PaymentVNPAYRequest {
    @JsonProperty("amount")
    Long amount;

    @JsonProperty("bankCode")
    String bankCode;

    @JsonProperty("language")
    String language;
}
