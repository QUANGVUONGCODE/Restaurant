package com.store.restaurant.mapper;

import com.store.restaurant.dto.request.CommentRequest;
import com.store.restaurant.dto.response.CommentResponse;
import com.store.restaurant.entity.Comment;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.8 (Oracle Corporation)"
)
@Component
public class CommentMapperImpl implements CommentMapper {

    @Override
    public Comment mapToComment(CommentRequest commentRequest) {
        if ( commentRequest == null ) {
            return null;
        }

        Comment.CommentBuilder comment = Comment.builder();

        comment.content( commentRequest.getContent() );
        comment.star( commentRequest.getStar() );

        return comment.build();
    }

    @Override
    public CommentResponse mapToCommentResponse(Comment comment) {
        if ( comment == null ) {
            return null;
        }

        CommentResponse.CommentResponseBuilder commentResponse = CommentResponse.builder();

        commentResponse.id( comment.getId() );
        commentResponse.content( comment.getContent() );
        commentResponse.user( comment.getUser() );
        commentResponse.star( comment.getStar() );
        commentResponse.createdAt( comment.getCreatedAt() );
        commentResponse.updatedAt( comment.getUpdatedAt() );

        return commentResponse.build();
    }

    @Override
    public void updateComment(CommentRequest commentRequest, Comment comment) {
        if ( commentRequest == null ) {
            return;
        }

        comment.setContent( commentRequest.getContent() );
        comment.setStar( commentRequest.getStar() );
    }
}
