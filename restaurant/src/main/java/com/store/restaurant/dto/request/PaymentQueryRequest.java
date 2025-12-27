package com.store.restaurant.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentQueryRequest {
    @JsonProperty("order_id")
    String orderId; // Mã đơn hàng cần truy vấn

    @JsonProperty("trans_date")
    String transDate; // Ngày giao dịch (định dạng yyyyMMddHHmmss)

    @JsonProperty("ip_address")
    String ipAddress; // Địa chỉ IP của người dùng
}
