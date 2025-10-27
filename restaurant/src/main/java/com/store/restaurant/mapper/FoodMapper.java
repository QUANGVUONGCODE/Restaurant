package com.store.restaurant.mapper;


import com.store.restaurant.dto.request.FoodRequest;
import com.store.restaurant.dto.request.requestUpdate.FoodRequestUpdate;
import com.store.restaurant.entity.Food;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface FoodMapper {
    Food mapToFood(FoodRequest request);
    FoodRequest toFoodResponse(Food food);
    void updateFood(FoodRequestUpdate foodRequest, @MappingTarget Food food);
}
