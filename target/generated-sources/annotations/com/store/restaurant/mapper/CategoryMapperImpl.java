package com.store.restaurant.mapper;

import com.store.restaurant.dto.request.CategoryRequest;
import com.store.restaurant.dto.response.CategoryResponse;
import com.store.restaurant.entity.Category;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.8 (Oracle Corporation)"
)
@Component
public class CategoryMapperImpl implements CategoryMapper {

    @Override
    public Category mapToCategory(CategoryRequest request) {
        if ( request == null ) {
            return null;
        }

        Category.CategoryBuilder category = Category.builder();

        category.name( request.getName() );

        return category.build();
    }

    @Override
    public CategoryResponse toCategoryResponse(Category category) {
        if ( category == null ) {
            return null;
        }

        CategoryResponse.CategoryResponseBuilder categoryResponse = CategoryResponse.builder();

        categoryResponse.id( category.getId() );
        categoryResponse.name( category.getName() );

        return categoryResponse.build();
    }

    @Override
    public void updateCategory(CategoryRequest categoryRequest, Category category) {
        if ( categoryRequest == null ) {
            return;
        }

        category.setName( categoryRequest.getName() );
    }
}
