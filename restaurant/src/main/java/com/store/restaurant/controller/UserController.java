package com.store.restaurant.controller;

import com.store.restaurant.dto.request.UserRequest;
import com.store.restaurant.dto.request.requestUpdate.UserRequestUpdate;
import com.store.restaurant.dto.response.ApiResponse;
import com.store.restaurant.dto.response.UserResponse;
import com.store.restaurant.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/user")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;

    @PostMapping
    ApiResponse<UserResponse> createUser(@RequestBody UserRequest userRequest){
        return ApiResponse.<UserResponse>builder()
                .result(userService.createUser(userRequest))
                .build();
    }

    @GetMapping
    ApiResponse<List<UserResponse>> getAllUser(){
        return ApiResponse.<List<UserResponse>>builder()
                .result(userService.getAllUser())
                .build();
    }

    @PutMapping("/{id}")
    ApiResponse<UserResponse> updateUser(@RequestBody UserRequestUpdate userRequestUpdate, @PathVariable Long id){
        return ApiResponse.<UserResponse>builder()
                .result(userService.updateUser(userRequestUpdate, id))
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<String> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return ApiResponse.<String>builder()
                .result("Delete user successfully")
                .build();
    }

    @GetMapping("/myInfo")
    ApiResponse<UserResponse> getMyInfo(){
        return ApiResponse.<UserResponse>builder()
                .result(userService.getMyInfor())
                .build();
    }

}
