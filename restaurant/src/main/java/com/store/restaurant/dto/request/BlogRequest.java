package com.store.restaurant.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BlogRequest {
    @NotBlank(message = "NOT_BLANK_NAME")
    String title;

    @NotBlank(message = "NOT_BLANK_NAME")
    String content;

    String thumbnail;
}
