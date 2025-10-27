package com.store.restaurant.repository;

import com.store.restaurant.entity.Food;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FoodReposiotry extends JpaRepository<Food, Long> {
    boolean existsByName(String name);

    @Query("SELECT p FROM Product p WHERE " +
            "(:categoryId IS NULL OR :categoryId = 0 OR p.category.id = :categoryId) " +
            "AND (:sectionId IS NULL OR :sectionId = 0 OR p.sections.id = :sectionId) " +
            "AND (:keyword IS NULL OR :keyword = '' OR p.description LIKE %:keyword% OR p.name LIKE %:keyword%)")
    Page<Food> findAll(Pageable pageable,
                       @Param("categoryId") Long categoryId,
                       @Param("sectionId") Long sectionId,
                       @Param("keyword") String keyword);


    List<Food> findByIdIn(@Param("foodIds") List<Long> foodIds);
}
