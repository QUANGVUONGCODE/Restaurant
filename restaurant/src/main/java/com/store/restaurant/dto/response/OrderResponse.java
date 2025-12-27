package com.store.restaurant.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.store.restaurant.entity.Payment;
import com.store.restaurant.entity.Table;
import com.store.restaurant.entity.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderResponse {
    Long id;

    @JsonProperty("order_code")
    String orderCode;

    @JsonProperty("user")
    User user;

    @JsonProperty("full_name")
    String fullName;

    @JsonProperty("email")
    String email;

    @JsonProperty("phone_number")
    String phoneNumber;

    @JsonProperty("number_of_guest")
    Integer numberOfGuest;

    @JsonProperty("table")
    Table table;

    @JsonProperty("note")
    String note;

    @JsonProperty("order_date")
    LocalDateTime orderDate;

    @JsonProperty("end_time")
    LocalDateTime endTime;

    @JsonProperty("status")
    String status;

    @JsonProperty("total_money")
    Float totalMoney;

    @JsonProperty("active")
    Boolean active;

    @JsonProperty("payment")
    Payment payment;
}
