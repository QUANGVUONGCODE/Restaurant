package com.store.restaurant.controller;

import com.store.restaurant.dto.request.BlogRequest;
import com.store.restaurant.dto.response.ApiResponse;
import com.store.restaurant.dto.response.BlogResponse;
import com.store.restaurant.entity.BlogImage;
import com.store.restaurant.exception.AppException;
import com.store.restaurant.exception.ErrorCode;
import com.store.restaurant.service.BlogImageService;
import com.store.restaurant.service.BlogService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("${api.prefix}/blog")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BlogController {
    BlogService blogService;
    BlogImageService blogImageService;

    @NonFinal
    @Value("${app.upload-dir}")
    String uploadDir;

    @PostMapping
    ApiResponse<BlogResponse> createBlog(@RequestBody BlogRequest request){
        return ApiResponse.<BlogResponse>builder()
                .result(blogService.create(request))
                .build();
    }


    @GetMapping("/{id}")
    ApiResponse<BlogResponse> getBlogById(@PathVariable Long id){
        return ApiResponse.<BlogResponse>builder()
                .result(blogService.getBlogById(id))
                .build();
    }

    @GetMapping
    ApiResponse<List<BlogResponse>> getAllBlogs(){
        return ApiResponse.<List<BlogResponse>>builder()
                .result(blogService.getAllBlogs())
                .build();
    }

    @PutMapping("/{id}")
    ApiResponse<BlogResponse> updateBlog(@PathVariable Long id, @RequestBody BlogRequest request){
        return ApiResponse.<BlogResponse>builder()
                .result(blogService.updateBlog(request, id))
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<Void> deleteBlog(@PathVariable Long id){
        blogService.deleteBlog(id);
        return ApiResponse.<Void>builder()
                .build();
    }

    @PostMapping("/upload/{id}")
    ApiResponse<?> uploadBlogImage(@PathVariable Long id, @RequestParam List<MultipartFile> files) throws IOException {
        try{
            List<BlogImage> blogImages = blogImageService.uploadBlogImage(files, id);
            return ApiResponse.<List<BlogImage>>builder()
                    .result(blogImages)
                    .build();
        }catch (Exception e){
            return ApiResponse.<String>builder()
                    .result(e.getMessage())
                    .build();
        }
    }

    @GetMapping("/images/{filename}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename){
        try{
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

    @GetMapping("/imageBlogs/{blogId}")
    ApiResponse<List<BlogImage>> getBlogImagesByBlogId(@PathVariable Long blogId){
        return ApiResponse.<List<BlogImage>>builder()
                .result(blogService.getBlogImagesByBlogId(blogId))
                .build();
    }

    @GetMapping("/count")
    ApiResponse<Long> countBlogs(){
        return ApiResponse.<Long>builder()
                .result(blogService.countBlogs())
                .build();
    }
}
