package com.store.restaurant.repository;

import com.store.restaurant.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Blog, Long> {
    Boolean existsByTitle(String title);
}
