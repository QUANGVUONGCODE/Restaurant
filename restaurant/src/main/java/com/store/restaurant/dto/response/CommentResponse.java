package com.store.restaurant.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.store.restaurant.entity.User;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentResponse {
    Long id;
    String content;

    @JsonProperty("user")
    User user;

    @JsonProperty("star")
    Long star;

    @JsonProperty("created_at")
    LocalDateTime createdAt;

    @JsonProperty("updated_at")
    LocalDateTime updatedAt;
}
