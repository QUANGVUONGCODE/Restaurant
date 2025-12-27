package com.store.restaurant.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRequest {
    @JsonProperty("full_name")
    String fullName;

    @Size(min = 10, max = 10, message = "PHONE_NUMBER_LENGTH")
    @JsonProperty("phone_number")
    String phoneNumber;

    @JsonProperty("address")
    String address;

    @NotBlank(message = "NOT_BLANK_EMAIL")
    @JsonProperty("email")
    String email;

    @JsonProperty("password")
    @Size(min = 8, max = 32, message = "PASSWORD_INVALID")
    String password;

    @JsonProperty("retype_password")
    @Size(min = 8, max = 32, message = "PASSWORD_INVALID")
    String retypePassword;

    @JsonProperty("date_of_birth")
    Date dateOfBirth;

    @JsonProperty("google_account_id")
    int facebookAccountId;

    @JsonProperty("facebook_account_id")
    int googleAccountId;
}
