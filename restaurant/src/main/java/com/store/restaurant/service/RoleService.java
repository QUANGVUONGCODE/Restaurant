package com.store.restaurant.service;

import com.store.restaurant.dto.request.RoleRequest;
import com.store.restaurant.dto.response.RoleResponse;
import com.store.restaurant.entity.Role;
import com.store.restaurant.exception.AppException;
import com.store.restaurant.exception.ErrorCode;
import com.store.restaurant.mapper.RoleMapper;
import com.store.restaurant.repository.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleService {
    RoleRepository roleRepository;
    RoleMapper roleMapper;

    public RoleResponse createRole(RoleRequest request){
        if(roleRepository.existsByName(request.getName())){
            throw new AppException(ErrorCode.ROLE_EXISTS);
        }

        Role role = roleMapper.mapToRole(request);
        return roleMapper.mapToRoleResponse(roleRepository.save(role));
    }

}