package com.store.restaurant.entity;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "order_details")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    Order order;

    @ManyToOne
    @JoinColumn(name = "food_id", nullable = false)
    Food food;

    @Column(name = "price", nullable = false)
    Float price;

    @Column(name = "quantity", nullable = false)
    Long quantity;

    @Column(name = "total_money", nullable = false)
    Float totalMoney;
}
