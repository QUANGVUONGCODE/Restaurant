package com.store.restaurant.configuration;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VNPayConfig {
    @Value("${vnpay.pay-url}")
    String vnpPayUrl;

    @Value("${vnpay.return-url}")
    String vnpReturnUrl;

    @Value("${vnpay.tmn-code}")
    String vnpTmnCode;

    @Value("${vnpay.secret-key}")
    String secretKey;

    @Value("${vnpay.api-url}")
    String vn;
}
