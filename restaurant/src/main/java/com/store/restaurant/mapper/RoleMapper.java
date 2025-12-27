package com.store.restaurant.mapper;

import com.store.restaurant.dto.request.RoleRequest;
import com.store.restaurant.dto.response.RoleResponse;
import com.store.restaurant.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    Role mapToRole(RoleRequest roleRequest);

    RoleResponse mapToRoleResponse(Role role);

    void updateRole(RoleRequest roleRequest, @MappingTarget Role role);
}
