package com.store.restaurant.repository;

import com.store.restaurant.entity.Food;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FoodRepository extends JpaRepository<Food, Long> {

    boolean existsByName(String name);

    @Query(value = """
        select distinct f
        from Food f
        left join f.sections s
        where (:categoryId is null or :categoryId = 0 or f.category.id = :categoryId)
          and (:sectionId  is null or :sectionId  = 0 or s.id = :sectionId)
          and (
               :keyword is null or :keyword = ''
               or lower(f.name) like lower(concat('%', :keyword, '%'))
               or lower(coalesce(f.description, '')) like lower(concat('%', :keyword, '%'))
          )
        """,
            countQuery = """
        select count(distinct f.id)
        from Food f
        left join f.sections s
        where (:categoryId is null or :categoryId = 0 or f.category.id = :categoryId)
          and (:sectionId  is null or :sectionId  = 0 or s.id = :sectionId)
          and (
               :keyword is null or :keyword = ''
               or lower(f.name) like lower(concat('%', :keyword, '%'))
               or lower(coalesce(f.description, '')) like lower(concat('%', :keyword, '%'))
          )
        """
    )
    Page<Food> searchFoods(Pageable pageable,
                           @Param("categoryId") Long categoryId,
                           @Param("sectionId")  Long sectionId,
                           @Param("keyword")    String keyword);

    List<Food> findByIdIn(List<Long> foodIds);
}
