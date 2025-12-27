package com.store.restaurant.service;

import com.store.restaurant.dto.request.UserRequest;
import com.store.restaurant.dto.request.requestUpdate.UserRequestUpdate;
import com.store.restaurant.dto.response.UserResponse;
import com.store.restaurant.entity.Role;
import com.store.restaurant.entity.User;
import com.store.restaurant.enums.RolePlay;
import com.store.restaurant.exception.AppException;
import com.store.restaurant.exception.ErrorCode;
import com.store.restaurant.mapper.UserMapper;
import com.store.restaurant.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;

    public UserResponse createUser(UserRequest userRequest){
        if(userRepository.existsByPhoneNumber(userRequest.getPhoneNumber())){
            throw new AppException(ErrorCode.PHONE_NUMBER_EXISTS);
        }
        if(userRepository.existsByEmail(userRequest.getEmail())){
            throw new AppException(ErrorCode.EMAIL_EXISTS);
        }

        if(userRequest.getFacebookAccountId() == 0 && userRequest.getGoogleAccountId() ==0){
            String password = userRequest.getPassword();
            userRequest.setPassword(password);
        }
        log.info("Password: {}", userRequest.getPassword());
        log.info("Retype Password: {}", userRequest.getRetypePassword());
        if(!userRequest.getPassword().equals(userRequest.getRetypePassword())){
            throw new AppException(ErrorCode.RETYPE_PASSWORD_WRONG);
        }
        User user = userMapper.mapToUser(userRequest);
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        Role role = new Role();
        role.setId(2L);
        role.setName(RolePlay.USER.name());
        user.setRole(role);
        user.setActive(true);
        return userMapper.mapToUserResponse(userRepository.save(user));
    }

    public List<UserResponse> getAllUser(){
        return userRepository.findAll().stream()
                .map(userMapper::mapToUserResponse)
                .toList();
    }

    public UserResponse updateUser(UserRequestUpdate requestUpdate, Long id){
        User user = userRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.INVALID_ID)
        );
        if(userRepository.existsByPhoneNumber(requestUpdate.getPhoneNumber())){
            throw new AppException(ErrorCode.PHONE_NUMBER_EXISTS);
        }
        if(userRepository.existsByEmail(requestUpdate.getEmail())){
            throw new AppException(ErrorCode.EMAIL_EXISTS);
        }

        userMapper.updateUser(requestUpdate, user);
        return userMapper.mapToUserResponse(userRepository.save(user));
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.INVALID_ID)
        );
        user.setActive(false);
        userRepository.save(user);
    }

    public UserResponse getMyInfor(){
        var context = SecurityContextHolder.getContext();
        String phoneNumber = context.getAuthentication().getName();
        log.info("ROLE:" + context.getAuthentication().getAuthorities());
        User user = userRepository.findByPhoneNumber(phoneNumber).orElseThrow(
                () -> new AppException(ErrorCode.USER_EXISTS)
        );
        return userMapper.mapToUserResponse(user);
    }
}
