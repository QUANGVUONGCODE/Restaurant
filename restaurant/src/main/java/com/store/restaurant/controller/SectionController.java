package com.store.restaurant.controller;

import com.store.restaurant.dto.request.SectionRequest;
import com.store.restaurant.dto.response.ApiResponse;
import com.store.restaurant.dto.response.SectionResponse;
import com.store.restaurant.service.SectionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/sections")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SectionController {
    SectionService sectionService;

    @PostMapping
    ApiResponse<SectionResponse> createSection(@RequestBody SectionRequest request){
        return ApiResponse.<SectionResponse>builder()
                .result(sectionService.createSection(request))
                .build();
    }

    @GetMapping("/{id}")
    ApiResponse<SectionResponse> getSectionById(@PathVariable Long id){
        return ApiResponse.<SectionResponse>builder()
                .result(sectionService.getSectionById(id))
                .build();
    }

    @GetMapping
    ApiResponse<List<SectionResponse>> getAllSections(){
        return ApiResponse.<List<SectionResponse>>builder()
                .result(sectionService.getAllSections())
                .build();
    }

    @PutMapping("/{id}")
    ApiResponse<SectionResponse> updateSection(@PathVariable Long id, @RequestBody SectionRequest request){
        return ApiResponse.<SectionResponse>builder()
                .result(sectionService.updateSection(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<String> deleteSection(@PathVariable Long id){
        sectionService.deleteSection(id);
        return ApiResponse.<String>builder()
                .result("Section deleted successfully")
                .build();
    }
}
