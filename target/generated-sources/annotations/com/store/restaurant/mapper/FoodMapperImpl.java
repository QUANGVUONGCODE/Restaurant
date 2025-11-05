package com.store.restaurant.mapper;

import com.store.restaurant.dto.request.FoodRequest;
import com.store.restaurant.dto.request.requestUpdate.FoodRequestUpdate;
import com.store.restaurant.dto.response.FoodResponse;
import com.store.restaurant.entity.Food;
import com.store.restaurant.entity.Section;
import java.util.ArrayList;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.8 (Oracle Corporation)"
)
@Component
public class FoodMapperImpl implements FoodMapper {

    @Override
    public Food mapToFood(FoodRequest request) {
        if ( request == null ) {
            return null;
        }

        Food.FoodBuilder food = Food.builder();

        food.name( request.getName() );
        food.price( request.getPrice() );
        food.thumbnail( request.getThumbnail() );
        food.description( request.getDescription() );
        food.bestSeller( request.getBestSeller() );
        food.active( request.getActive() );

        return food.build();
    }

    @Override
    public FoodResponse toFoodResponse(Food food) {
        if ( food == null ) {
            return null;
        }

        FoodResponse.FoodResponseBuilder foodResponse = FoodResponse.builder();

        foodResponse.id( food.getId() );
        foodResponse.name( food.getName() );
        foodResponse.description( food.getDescription() );
        foodResponse.price( food.getPrice() );
        foodResponse.thumbnail( food.getThumbnail() );
        foodResponse.category( food.getCategory() );
        Set<Section> set = food.getSections();
        if ( set != null ) {
            foodResponse.sections( new ArrayList<Section>( set ) );
        }
        foodResponse.createdAt( food.getCreatedAt() );
        foodResponse.updatedAt( food.getUpdatedAt() );
        foodResponse.bestSeller( food.getBestSeller() );
        foodResponse.active( food.getActive() );

        return foodResponse.build();
    }

    @Override
    public void updateFood(FoodRequestUpdate foodRequest, Food food) {
        if ( foodRequest == null ) {
            return;
        }

        food.setName( foodRequest.getName() );
        food.setPrice( foodRequest.getPrice() );
        food.setThumbnail( foodRequest.getThumbnail() );
        food.setDescription( foodRequest.getDescription() );
        food.setBestSeller( foodRequest.getBestSeller() );
        food.setActive( foodRequest.getActive() );
    }
}
