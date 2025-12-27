package com.store.restaurant.service;

import com.store.restaurant.dto.request.BlogImageRequest;
import com.store.restaurant.dto.request.BlogRequest;
import com.store.restaurant.dto.response.BlogResponse;
import com.store.restaurant.entity.Blog;
import com.store.restaurant.entity.BlogImage;
import com.store.restaurant.entity.Food;
import com.store.restaurant.exception.AppException;
import com.store.restaurant.exception.ErrorCode;
import com.store.restaurant.mapper.BlogMapper;
import com.store.restaurant.repository.BlogImageRepository;
import com.store.restaurant.repository.BlogRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BlogService {
    BlogRepository blogRepository;
    BlogMapper blogMapper;
    BlogImageRepository blogImageRepository;


    public BlogResponse create(BlogRequest request) {
        if(blogRepository.existsByTitle(request.getTitle())){
            throw new AppException(ErrorCode.BLOG_EXISTS);
        }
        Blog blog = blogMapper.mapToBlog(request);
        blog.prePersist();

        return blogMapper.toBlogResponse(blogRepository.save(blog));
    }

    public BlogResponse getBlogById(Long id){
        return blogMapper.toBlogResponse(blogRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.INVALID_ID)));
    }
    public List<BlogResponse> getAllBlogs(){
        return blogRepository.findAll().stream().map(blogMapper::toBlogResponse).toList();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public BlogResponse updateBlog(BlogRequest request, Long id){
        Blog blog = blogRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.INVALID_ID));
        blogMapper.updateBlog(request, blog);
        blog.preUpdate();
        return blogMapper.toBlogResponse(blogRepository.save(blog));
    }


    @PreAuthorize("hasRole('ADMIN')")
    public void deleteBlog(Long id){
        if(!blogRepository.existsById(id)){
            throw new AppException(ErrorCode.INVALID_ID);
        }
        blogImageRepository.deleteAll(
                blogImageRepository.findByBlogId(id)
        );
        blogRepository.deleteById(id);
    }


    @PreAuthorize("hasRole('ADMIN')")
    public BlogImage createBlogImage(Long blogId, BlogImageRequest request){
        Blog blog = blogRepository.findById(blogId).orElseThrow(
                () -> new AppException(ErrorCode.INVALID_ID)
        );
        BlogImage blogImage = BlogImage.builder()
                .blog(blog)
                .url(request.getUrl())
                .build();
        int size = blogImageRepository.findByBlogId(blogId).size();
        if(size >= BlogImage.MAX_IMAGE_COUNT){
            throw new AppException(ErrorCode.MAXIMUM_IMAGE_COUNT_EXCEEDED);
        }
        return blogImageRepository.save(blogImage);
    }

    public BlogImage getBlogImageByThumbnail(String thumbnail){
        return blogImageRepository.findByUrl(thumbnail).orElseThrow(
                () -> new AppException(ErrorCode.INVALID_THUMBNAIL)
        );
    }

    public List<BlogImage> getBlogImagesByBlogId(Long blogId){
        return blogImageRepository.findByBlogId(blogId);
    }

    public void updateBlogThumbnail(Long foodId, String thumbnailUrl) {
        Blog blog = blogRepository.findById(foodId).orElseThrow(
                () -> new AppException(ErrorCode.INVALID_ID)
        );
        blog.setThumbnail(thumbnailUrl);
        blogRepository.save(blog);
    }

    public Long countBlogs(){
        return blogRepository.count();
    }
}
