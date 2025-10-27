package com.store.restaurant.repository;

import com.store.restaurant.entity.Section;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SectionRepository extends JpaRepository<Section, Long> {
    boolean existsByName(String name);
}