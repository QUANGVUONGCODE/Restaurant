package com.store.restaurant.controller;

import com.store.restaurant.dto.request.RoleRequest;
import com.store.restaurant.dto.response.ApiResponse;
import com.store.restaurant.dto.response.RoleResponse;
import com.store.restaurant.service.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("${api.prefix}/roles")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleController {
    RoleService roleService;

    @PostMapping
    ApiResponse<RoleResponse> createRole(@RequestBody RoleRequest request){
        return ApiResponse.<RoleResponse>builder()
                .result(roleService.createRole(request))
                .build();
    }


//    @GetMapping
//    ApiResponse<List<RoleResponse>> getAll() {
//            return ApiResponse.<List<RoleResponse>>builder()
//                    .result(roleService.getAll())
//                    .build();
//    }
//
//    @GetMapping("/{id}")
//    ApiResponse<RoleResponse> getRoleById(@PathVariable Long id){
//        return ApiResponse.<RoleResponse>builder()
//                .result(roleService.getRoleById(id))
//                .build();
//    }
//
//    @DeleteMapping("/{id}")
//    ApiResponse<String> deleteRole(@PathVariable Long id){
//        roleService.delete(id);
//        return ApiResponse.<String>builder()
//                .result("Role deleted successfully")
//                .build();
//    }
}
