package com.store.restaurant.repository;

import com.store.restaurant.entity.FoodImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FoodImageRepository extends JpaRepository<FoodImage, Long> {
    List<FoodImage> findByFoodId(Long foodId);

    Optional<FoodImage> findByUrl(String url);
}
