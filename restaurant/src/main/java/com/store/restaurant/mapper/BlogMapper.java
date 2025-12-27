package com.store.restaurant.mapper;

import com.store.restaurant.dto.request.BlogRequest;
import com.store.restaurant.dto.response.BlogResponse;
import com.store.restaurant.entity.Blog;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BlogMapper {
    Blog mapToBlog(BlogRequest request);
    BlogResponse toBlogResponse(Blog blog);
    void updateBlog(BlogRequest request, @MappingTarget Blog blog);
}
