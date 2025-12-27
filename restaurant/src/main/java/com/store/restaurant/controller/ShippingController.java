package com.store.restaurant.controller;

import com.store.restaurant.dto.request.ShippingRequest;
import com.store.restaurant.dto.response.ApiResponse;
import com.store.restaurant.dto.response.ShippingResponse;
import com.store.restaurant.service.ShippingService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("${api.prefix}/shipping")
public class ShippingController {
    ShippingService shippingService;

    @PostMapping
    ApiResponse<ShippingResponse> createShipping(@RequestBody ShippingRequest request){
        return ApiResponse.<ShippingResponse>builder()
                .result(shippingService.createShipping(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<ShippingResponse>> getAllShipping(){
        return ApiResponse.<List<ShippingResponse>>builder()
                .result(shippingService.getAllShipping())
                .build();
    }

    @GetMapping("/{id}")
    ApiResponse<ShippingResponse> getShippingById(@PathVariable Long id){
        return ApiResponse.<ShippingResponse>builder()
                .result(shippingService.getShippingById(id))
                .build();
    }

    @PutMapping("/{id}")
    ApiResponse<ShippingResponse> updateShipping(@RequestBody ShippingRequest request, @PathVariable Long id){
        return ApiResponse.<ShippingResponse>builder()
                .result(shippingService.updateShipping(request, id))
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<String> deleteShipping(@PathVariable Long id){
        shippingService.deleteShipping(id);
        return ApiResponse.<String>builder()
                .result("Delete shipping successfully")
                .build();
    }
}
