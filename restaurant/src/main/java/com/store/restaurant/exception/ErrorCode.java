package com.store.restaurant.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    NOT_BLANK_NAME(1001, "Name must not be blank", HttpStatus.BAD_REQUEST),
    INVALID_KEY(1002, "Invalid key", HttpStatus.BAD_REQUEST ),
    CATEGORY_EXISTS(1003, "Category exists", HttpStatus.BAD_REQUEST),
    INVALID_ID(1004, "Invalid ID", HttpStatus.BAD_REQUEST),
    SECTION_EXISTS(1005, "Section exists", HttpStatus.BAD_REQUEST),
    FOOD_EXISTS(1006, "Food exists", HttpStatus.BAD_REQUEST),
    MAXIMUM_IMAGE_COUNT_EXCEEDED(1007, "Maximum image count exceeded", HttpStatus.BAD_REQUEST),
    INVALID_IMAGE(1008, "Invalid image", HttpStatus.BAD_REQUEST),
    INVALID_IMAGE_SIZE(1009, "Invalid image size bigger 5MB", HttpStatus.BAD_REQUEST),
    INVALID_IMAGE_URL(1010, "Invalid image URL", HttpStatus.BAD_REQUEST),
    INVALID_THUMBNAIL(1011, "Invalid thumbnail", HttpStatus.BAD_REQUEST),

    ;


    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private int code;
    private String message;
    private HttpStatusCode statusCode;
}
