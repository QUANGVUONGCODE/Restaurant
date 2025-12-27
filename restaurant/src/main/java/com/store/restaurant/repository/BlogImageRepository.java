package com.store.restaurant.repository;

import com.store.restaurant.entity.BlogImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BlogImageRepository extends JpaRepository<BlogImage, Long> {
    Optional<BlogImage> findByUrl(String url);

    List<BlogImage> findByBlogId(Long blogId);
}
