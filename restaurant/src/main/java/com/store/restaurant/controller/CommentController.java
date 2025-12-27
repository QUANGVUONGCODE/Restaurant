package com.store.restaurant.controller;

import com.store.restaurant.dto.request.CommentRequest;
import com.store.restaurant.dto.response.ApiResponse;
import com.store.restaurant.dto.response.CommentListResponse;
import com.store.restaurant.dto.response.CommentResponse;
import com.store.restaurant.service.CommentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/comment")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommentController {
    CommentService commentService;

    @PostMapping
    ApiResponse<CommentResponse> createComment(@RequestBody CommentRequest commentRequest){
        return ApiResponse.<CommentResponse>builder()
                .result(commentService.createComment(commentRequest))
                .build();
    }

    @GetMapping
    ApiResponse<CommentListResponse> getAllComments(
            @RequestParam(defaultValue = "", name = "star") Integer star,
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "10", name = "limit") int limit
    ){
        PageRequest pageRequest = PageRequest.of(
                page,
                limit,
                Sort.by("id").ascending()
        );
        Page<CommentResponse> commentpage = commentService.getAllComments(star, pageRequest);
        List<CommentResponse> comments = commentpage.getContent();
        int totalPage = commentpage.getNumber() + 1;
        return ApiResponse.<CommentListResponse>builder()
                .result(CommentListResponse.builder()
                        .comments(comments)
                        .totalPage(totalPage)
                        .build())
                .build();
    }



    @PutMapping("/{id}")
    ApiResponse<CommentResponse> updateComment(@RequestBody CommentRequest commentRequest, @PathVariable Long id){
        return ApiResponse.<CommentResponse>builder()
                .result(commentService.updateComment(commentRequest, id))
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<String> deleteComment(@PathVariable Long id){
        commentService.deleteComment(id);
        return ApiResponse.<String>builder()
                .result("Delete comment successfully")
                .build();
    }
}
