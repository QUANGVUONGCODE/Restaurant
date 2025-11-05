package com.store.restaurant.service;

import com.store.restaurant.dto.request.FoodImageRequest;
import com.store.restaurant.dto.request.FoodRequest;
import com.store.restaurant.dto.request.requestUpdate.FoodRequestUpdate;
import com.store.restaurant.dto.response.FoodResponse;
import com.store.restaurant.entity.Category;
import com.store.restaurant.entity.Food;
import com.store.restaurant.entity.FoodImage;
import com.store.restaurant.entity.Section;
import com.store.restaurant.exception.AppException;
import com.store.restaurant.exception.ErrorCode;
import com.store.restaurant.mapper.FoodMapper;
import com.store.restaurant.repository.CategoryRepository;
import com.store.restaurant.repository.FoodImageRepository;
import com.store.restaurant.repository.FoodRepository;
import com.store.restaurant.repository.SectionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FoodService {
    FoodRepository foodRepository;
    FoodMapper foodMapper;
    CategoryRepository categoryRepository;
    SectionRepository sectionRepository;
    FoodImageRepository foodImageRepository;

    public FoodResponse createFood(FoodRequest request){
        if (foodRepository.existsByName(request.getName())) {
            throw new AppException(ErrorCode.FOOD_EXISTS);
        }

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_ID));

        Food food = foodMapper.mapToFood(request);
        food.setCategory(category);

        // set optional flags
        if (request != null) food.setBestSeller(request.getBestSeller());
        if (request.getActive() != null) food.setActive(request.getActive());

        // sections
        Set<Long> sectionIds = new HashSet<>(Optional.ofNullable(request.getSectionIds()).orElse(List.of()));
        if (!sectionIds.isEmpty()) {
            List<Section> sections = sectionRepository.findAllById(sectionIds);
            if (sections.size() != sectionIds.size()) {
                throw new AppException(ErrorCode.INVALID_ID);
            }
            food.setSections(new HashSet<>(sections));
        } else {
            food.setSections(new HashSet<>());
        }

        food.prePersist();
        Food saved = foodRepository.save(food);
        return foodMapper.toFoodResponse(saved);
    }

    public FoodResponse getFoodById(Long id){
        return foodMapper.toFoodResponse(foodRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.INVALID_ID)));
    }

    public Page<FoodResponse> getAllFoods(String keyword, Long categoryId, Long sectionId, PageRequest pageRequest){
        return foodRepository
                .searchFoods(pageRequest, categoryId, sectionId, keyword)
                .map(foodMapper::toFoodResponse);
    }

    public FoodResponse updateFood(FoodRequestUpdate requestUpdate, Long id){
        if (id == null) {
            throw new AppException(ErrorCode.INVALID_ID);
        }

        Food food = foodRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.INVALID_ID)
        );

        // only update category when a categoryId is provided to avoid passing null to findById
        if (requestUpdate != null && requestUpdate.getCategoryId() != null) {
            Category category = categoryRepository.findById(requestUpdate.getCategoryId())
                    .orElseThrow(() -> new AppException(ErrorCode.INVALID_ID));
            food.setCategory(category);
        }

        foodMapper.updateFood(requestUpdate, food);

        Set<Long> sectionIds = new HashSet<>(Optional.ofNullable(requestUpdate.getSectionIds()).orElse(List.of()));
        if (!sectionIds.isEmpty()) {
            List<Section> sections = sectionRepository.findAllById(sectionIds);
            if (sections.size() != sectionIds.size()) {
                throw new AppException(ErrorCode.INVALID_ID);
            }
            food.setSections(new HashSet<>(sections));
        } else {
            food.setSections(new HashSet<>());
        }

        food.preUpdate();
        return foodMapper.toFoodResponse(foodRepository.save(food));
    }

    public void deleteFood(Long id){
        if(!foodRepository.existsById(id)){
            throw new AppException(ErrorCode.INVALID_ID);
        }
        foodRepository.deleteById(id);
    }

    public List<Food> getFoodsByIds(List<Long> foodIds){
        return foodRepository.findByIdIn(foodIds);
    }

    public List<FoodImage> getFoodImageById(Long id){
        return foodImageRepository.findByFoodId(id);
    }

    public FoodImage getFoodImageByThumbnail(String thumbanail){
        return foodImageRepository.findByUrl(thumbanail).orElseThrow(
                () -> new AppException(ErrorCode.INVALID_THUMBNAIL)
        );
    }

    public FoodImage createFoodImage(Long foodId, FoodImageRequest request){
        Food food = foodRepository.findById(foodId).orElseThrow(
                () -> new AppException(ErrorCode.INVALID_ID)
        );
        FoodImage foodImage = FoodImage.builder()
                .food(food)
                .url(request.getUrl())
                .build();
        int size = foodImageRepository.findByFoodId(foodId).size();
        if(size >= FoodImage.MAXIMUM_IMAGE_COUNT){
            throw new AppException(ErrorCode.MAXIMUM_IMAGE_COUNT_EXCEEDED);
        }
        return foodImageRepository.save(foodImage);
    }
}
