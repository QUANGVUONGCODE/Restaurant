package com.store.restaurant.repository;

import com.store.restaurant.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    boolean existsByName(String name);
}
