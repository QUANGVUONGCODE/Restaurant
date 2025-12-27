package com.store.restaurant.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.store.restaurant.entity.Table;
public interface TableRepository extends JpaRepository<Table, Long> {
    boolean existsByName(String name);
}
