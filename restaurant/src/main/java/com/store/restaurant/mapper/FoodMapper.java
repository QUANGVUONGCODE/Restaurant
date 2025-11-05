package com.store.restaurant.mapper;


import com.store.restaurant.dto.request.FoodRequest;
import com.store.restaurant.dto.request.requestUpdate.FoodRequestUpdate;
import com.store.restaurant.dto.response.FoodResponse;
import com.store.restaurant.entity.Category;
import com.store.restaurant.entity.Food;
import com.store.restaurant.entity.Section;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface FoodMapper {
    Food mapToFood(FoodRequest request);
    FoodResponse toFoodResponse(Food food);
    void updateFood(FoodRequestUpdate foodRequest, @MappingTarget Food food);
}
