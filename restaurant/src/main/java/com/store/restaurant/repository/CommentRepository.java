package com.store.restaurant.repository;

import com.store.restaurant.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT c FROM Comment c WHERE (c.star = :star OR :star IS NULL)")
    Page<Comment> searchComments(
            Pageable pageable,
            @Param("star") Integer star
    );
}
