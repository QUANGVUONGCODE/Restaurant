package com.store.restaurant.mapper;

import com.store.restaurant.dto.request.UserRequest;
import com.store.restaurant.dto.request.requestUpdate.UserRequestUpdate;
import com.store.restaurant.dto.response.UserResponse;
import com.store.restaurant.entity.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.8 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User mapToUser(UserRequest request) {
        if ( request == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.fullName( request.getFullName() );
        user.phoneNumber( request.getPhoneNumber() );
        user.address( request.getAddress() );
        user.email( request.getEmail() );
        user.password( request.getPassword() );
        user.retypePassword( request.getRetypePassword() );
        user.dateOfBirth( request.getDateOfBirth() );
        user.facebookAccountId( request.getFacebookAccountId() );
        user.googleAccountId( request.getGoogleAccountId() );

        return user.build();
    }

    @Override
    public UserResponse mapToUserResponse(User user) {
        if ( user == null ) {
            return null;
        }

        UserResponse.UserResponseBuilder userResponse = UserResponse.builder();

        userResponse.id( user.getId() );
        userResponse.fullName( user.getFullName() );
        userResponse.phoneNumber( user.getPhoneNumber() );
        userResponse.address( user.getAddress() );
        userResponse.email( user.getEmail() );
        userResponse.password( user.getPassword() );
        userResponse.retypePassword( user.getRetypePassword() );
        userResponse.dateOfBirth( user.getDateOfBirth() );
        userResponse.facebookAccountId( user.getFacebookAccountId() );
        userResponse.googleAccountId( user.getGoogleAccountId() );
        userResponse.active( user.getActive() );
        userResponse.createdAt( user.getCreatedAt() );
        userResponse.updatedAt( user.getUpdatedAt() );
        userResponse.role( user.getRole() );

        return userResponse.build();
    }

    @Override
    public void updateUser(UserRequestUpdate requestUpdate, User user) {
        if ( requestUpdate == null ) {
            return;
        }

        if ( requestUpdate.getFullName() != null ) {
            user.setFullName( requestUpdate.getFullName() );
        }
        if ( requestUpdate.getPhoneNumber() != null ) {
            user.setPhoneNumber( requestUpdate.getPhoneNumber() );
        }
        if ( requestUpdate.getAddress() != null ) {
            user.setAddress( requestUpdate.getAddress() );
        }
        if ( requestUpdate.getEmail() != null ) {
            user.setEmail( requestUpdate.getEmail() );
        }
        if ( requestUpdate.getPassword() != null ) {
            user.setPassword( requestUpdate.getPassword() );
        }
        if ( requestUpdate.getRetypePassword() != null ) {
            user.setRetypePassword( requestUpdate.getRetypePassword() );
        }
        if ( requestUpdate.getDateOfBirth() != null ) {
            user.setDateOfBirth( requestUpdate.getDateOfBirth() );
        }
    }
}
