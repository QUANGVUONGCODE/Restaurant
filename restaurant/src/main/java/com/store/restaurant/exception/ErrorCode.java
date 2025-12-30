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
    PERMISSION_EXISTS(1012, "Permission exists", HttpStatus.BAD_REQUEST),
    ID_PERMISSION_NOT_FOUND(1013, "Permission not found", HttpStatus.BAD_REQUEST),
    ROLE_EXISTS(1014, "Role exists", HttpStatus.BAD_REQUEST),
    ID_ROLE_NOT_FOUND(1015, "Role not found", HttpStatus.BAD_REQUEST),
    PERMISSION_NOT_FOUND(1016, "Permission not found", HttpStatus.BAD_REQUEST),
    TABLE_EXISTS(1017, "Table exists", HttpStatus.BAD_REQUEST),
    ID_TABLE_NOT_FOUND(1018, "Table not found", HttpStatus.BAD_REQUEST),
    ID_BLOG_IMAGE_NOT_FOUND(1019, "Id with Blog image not found", HttpStatus.BAD_REQUEST),
    BLOG_EXISTS(1020, "Blog exists", HttpStatus.BAD_REQUEST),
    PHONE_NUMBER_LENGTH(1021, "Phone number length must be 10", HttpStatus.BAD_REQUEST),
    NOT_BLANK_EMAIL(1022, "Email must not be blank", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(1023, "Password invalid", HttpStatus.BAD_REQUEST),
    PHONE_NUMBER_EXISTS(1024, "Phone number exists", HttpStatus.BAD_REQUEST),
    EMAIL_EXISTS(1025, "Email exists", HttpStatus.BAD_REQUEST),
    RETYPE_PASSWORD_WRONG(1026, "Retype password wrong", HttpStatus.BAD_REQUEST),
    SHIPPING_EXISTS(1027, "Shipping exists", HttpStatus.BAD_REQUEST),
    USER_ID_REQUIRED(1028, "User ID required", HttpStatus.BAD_REQUEST),
    INVALID_ORDER_ID(1029, "Invalid order ID", HttpStatus.BAD_REQUEST),
    INVALID_FOOD_ID(1030, "Invalid food ID", HttpStatus.BAD_REQUEST),
    INVALID_USER_ID(1031, "Invalid user ID", HttpStatus.BAD_REQUEST),
    INVALID_TABLE_ID(1032, "Invalid table ID", HttpStatus.BAD_REQUEST),
    INVALID_FOOD_PRICE(1033, "Invalid food price", HttpStatus.BAD_REQUEST),
    INVALID_FOOD_QUANTITY(1034, "Invalid food quantity", HttpStatus.BAD_REQUEST),
    INVALID_ID_SECTION(1035, "Invalid ID section", HttpStatus.BAD_REQUEST),
    PAYMENT_EXISTS(1036, "Payment exists", HttpStatus.BAD_REQUEST),
    INVALID_PAYMENT_ID(1037, "Invalid payment ID", HttpStatus.BAD_REQUEST),
    INVALID_TIME_RANGE(1038, "Invalid time range", HttpStatus.BAD_REQUEST),
    INVALID_NUMBER_OF_GUEST(1039, "Invalid number of guest", HttpStatus.BAD_REQUEST),
    INVALID_TABLE_STATUS(1040, "Invalid table status", HttpStatus.BAD_REQUEST),
    PHONE_NUMBER_INVALID(1041, "Phone number invalid", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTS(1042, "User not exists", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(1043, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    INVALID_TOKEN(1044, "Invalid token", HttpStatus.UNAUTHORIZED),
    JWT_ERROR(1045, "JWT error", HttpStatus.UNAUTHORIZED),
    USER_EXISTS(1046, "User exists", HttpStatus.BAD_REQUEST),
    RESERVATION_NOT_FOUND(1047, "Reservation not found", HttpStatus.BAD_REQUEST),
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
