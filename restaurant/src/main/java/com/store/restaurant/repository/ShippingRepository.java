package com.store.restaurant.repository;

import com.store.restaurant.entity.Shipping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShippingRepository extends JpaRepository<Shipping, Long> {
    boolean existsByName(String name);
}
