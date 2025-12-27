package com.store.restaurant.mapper;

import com.store.restaurant.dto.request.RoleRequest;
import com.store.restaurant.dto.response.RoleResponse;
import com.store.restaurant.entity.Role;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.8 (Oracle Corporation)"
)
@Component
public class RoleMapperImpl implements RoleMapper {

    @Override
    public Role mapToRole(RoleRequest roleRequest) {
        if ( roleRequest == null ) {
            return null;
        }

        Role.RoleBuilder role = Role.builder();

        role.name( roleRequest.getName() );

        return role.build();
    }

    @Override
    public RoleResponse mapToRoleResponse(Role role) {
        if ( role == null ) {
            return null;
        }

        RoleResponse.RoleResponseBuilder roleResponse = RoleResponse.builder();

        roleResponse.id( role.getId() );
        roleResponse.name( role.getName() );

        return roleResponse.build();
    }

    @Override
    public void updateRole(RoleRequest roleRequest, Role role) {
        if ( roleRequest == null ) {
            return;
        }

        role.setName( roleRequest.getName() );
    }
}
