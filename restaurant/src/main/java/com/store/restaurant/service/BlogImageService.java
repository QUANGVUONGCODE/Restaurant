package com.store.restaurant.service;

import com.store.restaurant.dto.request.BlogImageRequest;
import com.store.restaurant.dto.response.BlogImageResponse;
import com.store.restaurant.dto.response.BlogResponse;
import com.store.restaurant.entity.BlogImage;
import com.store.restaurant.exception.AppException;
import com.store.restaurant.exception.ErrorCode;
import com.store.restaurant.repository.BlogImageRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BlogImageService {
    BlogImageRepository blogImageRepository;
    BlogService blogService;
    FoodImageService foodImageService;

    public void deleteBlogImage(Long id){
        List<BlogImage> blogImages = blogImageRepository.findByBlogId(id);
        if(!blogImages.isEmpty()){
            blogImageRepository.deleteAll(blogImages);
        }
    }

    public List<BlogImage> uploadBlogImage(List<MultipartFile> files, Long id) throws IOException {
        BlogResponse blogExisting = blogService.getBlogById(id);
        files = files == null ? new ArrayList<>() : files;
        if(files.size() > BlogImage.MAX_IMAGE_COUNT){
            throw new AppException(ErrorCode.MAXIMUM_IMAGE_COUNT_EXCEEDED);
        }
        if(files.isEmpty() || files.stream().allMatch(file -> file.getSize() == 0)){
            throw new AppException(ErrorCode.INVALID_IMAGE);
        }

        List<BlogImage> blogImages = new ArrayList<>();
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
            String fileName = foodImageService.storeFile(file);
            BlogImage blogImage = blogService.createBlogImage(
                    blogExisting.getId(),
                    BlogImageRequest.builder()
                            .url(fileName)
                            .build()
            );
            blogImages.add(blogImage);
            if(blogImages.size() == 1){
                blogService.updateBlogThumbnail(id, fileName);
            }
        }
        return blogImages;
    }

}
