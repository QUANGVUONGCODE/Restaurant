package com.store.restaurant.mapper;

import com.store.restaurant.dto.request.CommentRequest;
import com.store.restaurant.dto.response.CommentResponse;
import com.store.restaurant.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    Comment mapToComment(CommentRequest commentRequest);
    CommentResponse mapToCommentResponse(Comment comment);
    void updateComment(CommentRequest commentRequest, @MappingTarget Comment comment);
}
