package com.store.restaurant.mapper;

import com.store.restaurant.dto.request.CategoryRequest;
import com.store.restaurant.dto.response.CategoryResponse;
import com.store.restaurant.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category mapToCategory(CategoryRequest request);
    CategoryResponse toCategoryResponse(Category category);
    void updateCategory(CategoryRequest categoryRequest, @MappingTarget Category category);
}
