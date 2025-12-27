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
public class PaymentRefundRequest {
    @JsonProperty("transaction_type")
    String transactionType; // Loại giao dịch hoàn tiền (vd: Refund)

    @JsonProperty("order_id")
    String orderId; // Mã đơn hàng cần hoàn tiền

    @JsonProperty("amount")
    Long amount; // Số tiền hoàn trả (đơn vị VND)

    @JsonProperty("transaction_date")
    String transactionDate; // Ngày giao dịch (định dạng yyyyMMddHHmmss)

    @JsonProperty("created_by")
    String createdBy; // Người thực hiện hoàn tiền

    @JsonProperty("ip_address")
    String ipAddress; // Địa chỉ IP của người thực hiện giao dịch
}
