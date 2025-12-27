package com.store.restaurant.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BlogImageRequest {
    @JsonProperty(value = "blog_id")
    Long blogId;

    @JsonProperty(value = "image_url")
    String url;
}
