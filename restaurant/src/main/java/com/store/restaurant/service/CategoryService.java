package com.store.restaurant.service;

import com.store.restaurant.dto.request.CategoryRequest;
import com.store.restaurant.dto.response.CategoryResponse;
import com.store.restaurant.entity.Category;
import com.store.restaurant.exception.AppException;
import com.store.restaurant.exception.ErrorCode;
import com.store.restaurant.mapper.CategoryMapper;
import com.store.restaurant.repository.CategoryRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryService {
    CategoryRepository categoryRepository;
    CategoryMapper categoryMapper;

    public CategoryResponse createCategory(CategoryRequest request) {
        if(categoryRepository.existsByName(request.getName())){
            throw new AppException(ErrorCode.CATEGORY_EXISTS);
        }
        Category category = categoryMapper.mapToCategory(request);
        return categoryMapper.toCategoryResponse(categoryRepository.save(category));
    }

    public List<CategoryResponse> getAllCategories(){
        return categoryRepository.findAll().stream().map(categoryMapper::toCategoryResponse).toList();
    }

    public CategoryResponse getCategoryById(Long id){
        return categoryMapper.toCategoryResponse(categoryRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.INVALID_ID)
        ));
    }

    public CategoryResponse updateCategory(Long id, CategoryRequest request){
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.INVALID_ID)
        );
        if(categoryRepository.existsByName(request.getName())){
            throw new AppException(ErrorCode.CATEGORY_EXISTS);
        }
        category.setName(request.getName());
        return categoryMapper.toCategoryResponse(categoryRepository.save(category));
    }

    public void deleteCategory(long id){
        if(!categoryRepository.existsById(id)){
            throw new AppException(ErrorCode.INVALID_ID);
        }
        categoryRepository.deleteById(id);
    }

    public Long countAllCategories(){
        return categoryRepository.count();
    }
}
