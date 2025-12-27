package com.store.restaurant.mapper;

import com.store.restaurant.dto.request.BlogRequest;
import com.store.restaurant.dto.response.BlogResponse;
import com.store.restaurant.entity.Blog;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.8 (Oracle Corporation)"
)
@Component
public class BlogMapperImpl implements BlogMapper {

    @Override
    public Blog mapToBlog(BlogRequest request) {
        if ( request == null ) {
            return null;
        }

        Blog.BlogBuilder blog = Blog.builder();

        blog.title( request.getTitle() );
        blog.content( request.getContent() );
        blog.thumbnail( request.getThumbnail() );

        return blog.build();
    }

    @Override
    public BlogResponse toBlogResponse(Blog blog) {
        if ( blog == null ) {
            return null;
        }

        BlogResponse.BlogResponseBuilder blogResponse = BlogResponse.builder();

        blogResponse.id( blog.getId() );
        blogResponse.title( blog.getTitle() );
        blogResponse.content( blog.getContent() );
        blogResponse.thumbnail( blog.getThumbnail() );
        blogResponse.createdAt( blog.getCreatedAt() );
        blogResponse.updatedAt( blog.getUpdatedAt() );

        return blogResponse.build();
    }

    @Override
    public void updateBlog(BlogRequest request, Blog blog) {
        if ( request == null ) {
            return;
        }

        blog.setTitle( request.getTitle() );
        blog.setContent( request.getContent() );
        blog.setThumbnail( request.getThumbnail() );
    }
}
