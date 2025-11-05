package com.store.restaurant.controller;

import com.store.restaurant.dto.request.FoodRequest;
import com.store.restaurant.dto.request.requestUpdate.FoodRequestUpdate;
import com.store.restaurant.dto.response.ApiResponse;
import com.store.restaurant.dto.response.FoodListResponse;
import com.store.restaurant.dto.response.FoodResponse;
import com.store.restaurant.entity.Food;
import com.store.restaurant.entity.FoodImage;
import com.store.restaurant.exception.AppException;
import com.store.restaurant.exception.ErrorCode;
import com.store.restaurant.service.FoodImageService;
import com.store.restaurant.service.FoodService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.prefix}/foods")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FoodController {
    FoodService foodService;
    FoodImageService foodImageService;

    @NonFinal
    @Value("${app.upload-dir}")
    String uploadDir;

    @PostMapping
    ApiResponse<FoodResponse> createFood(@RequestBody FoodRequest request){
        return ApiResponse.<FoodResponse>builder()
                .result(foodService.createFood(request))
                .build();
    }

    @GetMapping("/{id}")
    ApiResponse<FoodResponse> getFoodById(@PathVariable Long id){
        return ApiResponse.<FoodResponse>builder()
                .result(foodService.getFoodById(id))
                .build();
    }

    @GetMapping
    ApiResponse<FoodListResponse> getAllFoods(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "", name = "category_id") Long categoryId,
            @RequestParam(defaultValue = "", name = "section_id") Long sectionId,
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "10", name = "limit") int limit
    ){
        PageRequest pageRequest = PageRequest.of(
                page,
                limit,
                Sort.by("id").ascending()
        );
        Page<FoodResponse> foodPage = foodService.getAllFoods(keyword,categoryId,sectionId,pageRequest);
        List<FoodResponse> foods = foodPage.getContent();
        int totalPages = foodPage.getNumber() + 1;
        return ApiResponse.<FoodListResponse>builder()
                .result(FoodListResponse.builder()
                        .foodResponseList(foods)
                        .page(totalPages)
                        .build())
                .build();
    }

    @PutMapping("/{id}")
    ApiResponse<FoodResponse> updateFood(@PathVariable Long id, @RequestBody FoodRequestUpdate requestUpdate){
        return ApiResponse.<FoodResponse>builder()
                .result(foodService.updateFood(requestUpdate, id))
                .build();
    }


    @DeleteMapping("/{id}")
    ApiResponse<String> deleteFood(@PathVariable Long id){
        foodService.deleteFood(id);
        return ApiResponse.<String>builder()
                .result("Food deleted successfully")
                .build();
    }

    @GetMapping("/by-ids")
    ApiResponse<?> findFoodByIds(@RequestParam String ids){
        try {
            List<Long> foodIds = Arrays.stream(ids.split(",")).map(Long::parseLong).collect(Collectors.toList());
            return ApiResponse.<List<Food>>builder()
                    .result(foodService.getFoodsByIds(foodIds))
                    .build();
        }catch (Exception e){
            return ApiResponse.<String>builder()
                    .result(e.getMessage())
                    .build();
        }
    }


    @PostMapping("/upload/{id}")
    ApiResponse<?> uploadFoodImage(@PathVariable Long id, @RequestParam("files")List<MultipartFile> files) throws IOException{
        try {
            List<FoodImage> foodImages = foodImageService.uploadFoodImage(files, id);
            return ApiResponse.<List<FoodImage>>builder()
                    .result(foodImages)
                    .build();
        }catch (AppException e){
            return ApiResponse.<String>builder()
                    .result(e.getMessage())
                    .build();
        }
    }

    @GetMapping("/images/{filename}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename){
        try {
            Path filePath = Paths.get(uploadDir).resolve(filename);
            Resource resource = new UrlResource(filePath.toUri());
            if(!resource.exists() || !resource.isReadable()){
                throw new AppException(ErrorCode.INVALID_IMAGE);
            }
            String contentType = Files.probeContentType(filePath);
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(resource);
        }catch (Exception e){
            throw new AppException(ErrorCode.INVALID_IMAGE);
        }
    }

}
