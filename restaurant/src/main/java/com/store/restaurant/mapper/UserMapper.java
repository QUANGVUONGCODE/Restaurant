package com.store.restaurant.mapper;

import com.store.restaurant.dto.request.UserRequest;
import com.store.restaurant.dto.request.requestUpdate.UserRequestUpdate;
import com.store.restaurant.dto.response.UserResponse;
import com.store.restaurant.entity.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User mapToUser(UserRequest request);
    UserResponse mapToUserResponse(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUser(UserRequestUpdate requestUpdate, @MappingTarget User user);
}
