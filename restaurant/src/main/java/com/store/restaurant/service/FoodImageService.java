package com.store.restaurant.service;

import com.store.restaurant.dto.request.FoodImageRequest;
import com.store.restaurant.dto.response.FoodResponse;
import com.store.restaurant.entity.FoodImage;
import com.store.restaurant.exception.AppException;
import com.store.restaurant.exception.ErrorCode;
import com.store.restaurant.repository.FoodImageRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FoodImageService {
    FoodImageRepository foodImageRepository;
    FoodService foodService;

    @NonFinal
    @Value("${app.upload-dir}")
    String uploadRoot;

    public void deleteFoodImage(Long id){
        List<FoodImage> foodImages = foodImageRepository.findByFoodId(id);
        if(!foodImages.isEmpty()){
            foodImageRepository.deleteAll(foodImages);
        }
    }

    public List<FoodImage> uploadFoodImage(List<MultipartFile> files, Long id) throws IOException{
        FoodResponse foodExisting = foodService.getFoodById(id);
        files = files == null ? new ArrayList<>() : files;
        if(files.size() > FoodImage.MAXIMUM_IMAGE_COUNT){
            throw new AppException(ErrorCode.MAXIMUM_IMAGE_COUNT_EXCEEDED);
        }
        if(files.isEmpty() || files.stream().allMatch(file -> file.getSize() == 0)){
            throw new AppException(ErrorCode.INVALID_IMAGE);
        }
        List<FoodImage> foodImages = new ArrayList<>();
        for(MultipartFile file : files){
            if(file.getSize() == 0){
                continue;
            }
            if(file.getSize() > 1024*1024*5){
                throw new AppException(ErrorCode.INVALID_IMAGE_SIZE);
            }
            String contentType = file.getContentType();
            if(contentType == null || !contentType.startsWith("image/")){
                throw new AppException(ErrorCode.INVALID_IMAGE_URL);
            }
            String fileName = storeFile(file);
            FoodImage foodImage = foodService.createFoodImage(
                    foodExisting.getId(),
                    FoodImageRequest.builder()
                            .url(fileName)
                            .build()
            );
            foodImages.add(foodImage);
        }
        return foodImages;
    }

    private boolean isImageFile(MultipartFile file){
        String contentType = file.getContentType();
        return contentType != null && contentType.startsWith("image/");
    }

    public String storeFile(MultipartFile file) throws IOException {
        if(!isImageFile(file) && file.getOriginalFilename() == null){
            throw new AppException(ErrorCode.INVALID_IMAGE_URL);
        }
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String uniqueFileName = UUID.randomUUID().toString() + "_" + fileName;
        Path uploadDir = Paths.get(uploadRoot);
        if(!Files.exists(uploadDir)){
            Files.createDirectories(uploadDir);
        }
        Path destinationFile = Paths.get(uploadDir.toString(), uniqueFileName);
        Files.copy(file.getInputStream(), destinationFile, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFileName;
    }
}
