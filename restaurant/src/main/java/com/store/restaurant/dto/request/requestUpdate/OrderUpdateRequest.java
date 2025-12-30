package com.store.restaurant.dto.request.requestUpdate;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.store.restaurant.dto.request.CartRequest;
import com.store.restaurant.enums.OrderStatus;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderUpdateRequest {
    @JsonProperty("user_id")
    Long userId;

    @JsonProperty("full_name")
    String fullName;

    String email;

    @JsonProperty("phone_number")
    @Size(min = 10, max = 10, message = "PHONE_NUMBER_INVALID")
    String phoneNumber;

    String note;

    @JsonProperty("number_of_guest")
    Long numberOfGuest;


    @JsonProperty("table_id")
    Long tableId;

    @JsonProperty("status")
    OrderStatus status;

    @JsonProperty("cart_items")
    List<CartRequest> cartItems;

    @JsonProperty("vnp_txn_ref")
    String vnpTxnRef;
}
