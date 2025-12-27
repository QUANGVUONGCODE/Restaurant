package com.store.restaurant.service;

import com.store.restaurant.dto.request.CommentRequest;
import com.store.restaurant.dto.response.CommentResponse;
import com.store.restaurant.entity.Comment;
import com.store.restaurant.entity.User;
import com.store.restaurant.exception.AppException;
import com.store.restaurant.exception.ErrorCode;
import com.store.restaurant.mapper.CommentMapper;
import com.store.restaurant.repository.CommentRepository;
import com.store.restaurant.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommentService {
    CommentRepository commentRepository;
    CommentMapper commentMapper;
    UserRepository userRepository;

    @PreAuthorize("hasRole('USER')")
    public CommentResponse createComment(CommentRequest commentRequest){

        User user = userRepository.findById(commentRequest.getUserId()).orElseThrow(
                () -> new AppException(ErrorCode.INVALID_ID)
        );
        Comment comment = commentMapper.mapToComment(commentRequest);
        comment.setUser(user);
        return commentMapper.mapToCommentResponse(commentRepository.save(comment));
    }

//    public List<CommentResponse> getAllComment(){
//        return commentRepository.findAll().stream()
//                .map(commentMapper::mapToCommentResponse)
//                .toList();
//    }

    public Page<CommentResponse> getAllComments(Integer star, PageRequest pageRequest){
        return commentRepository.searchComments(pageRequest, star)
                .map(commentMapper::mapToCommentResponse);
    }

    public CommentResponse updateComment(CommentRequest commentRequest, Long id){
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.INVALID_ID)
        );
        commentMapper.updateComment(commentRequest, comment);
        return commentMapper.mapToCommentResponse(commentRepository.save(comment));
    }

    public void deleteComment(Long id){
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.INVALID_ID)
        );
        commentRepository.delete(comment);
    }
}
