package com.store.restaurant.entity;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Entity
@Table(name = "orders")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;


    @Column(name = "order_code",unique = true, nullable = false, length = 50)
    String orderCode;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @Column(name = "full_name", nullable = false, length = 255)
    String fullName;

    @Column(name = "email", length = 100)
    String email;

    @Column(name = "phone_number", nullable = false, length = 10)
    String phoneNumber;

    @Column(name = "number_of_guest")
    Integer numberOfGuest;

    @ManyToOne
    @JoinColumn(name = "table_id")
    com.store.restaurant.entity.Table table;

    @Column(name = "note")
    String note;

    @Column(name = "order_date")
    LocalDateTime orderDate;

    @Column(name = "end_time")
    LocalDateTime endTime;

    @Column(name = "created_at")
    LocalDateTime createdAt;

    @Column(name = "updated_at")
    LocalDateTime updatedAt;

    @Column(name = "status")
    String status;

    @Column(name = "total_money")
    Float totalMoney;

    @Column(name = "active")
    Boolean active;

    @Column(name = "vnp_txn_ref")
    String vnpTxnRef;

    @JoinColumn(name = "payment_id")
    @ManyToOne
    Payment payment;

    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
        if (this.orderCode == null || this.orderCode.isEmpty()) {
            this.orderCode = generateUniqueOrderCode();
        }
    }

    @PreUpdate
    public  void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    private String generateUniqueOrderCode() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        String timestamp = LocalDateTime.now().format(formatter);
        String randomSuffix = String.format("%03d", (int) (Math.random() * 1000000));

        return "ORD-" + timestamp + randomSuffix;
    }
}
