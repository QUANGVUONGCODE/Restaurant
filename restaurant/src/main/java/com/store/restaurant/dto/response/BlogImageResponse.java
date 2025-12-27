package com.store.restaurant.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BlogImageResponse {
    Long id;

    @JsonProperty(value = "blog_id")
    Long blogId;

    @JsonProperty(value = "image_url")
    String url;
}
