package com.store.restaurant.configuration;

import com.store.restaurant.entity.Role;
import com.store.restaurant.entity.User;
import com.store.restaurant.enums.RolePlay;
import com.store.restaurant.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

@Configuration
public class ApplicationInitConfig {
    private final UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public ApplicationInitConfig(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Bean
    ApplicationRunner applicationRunner(){
        return args -> {
            if(userRepository.findByPhoneNumber("admin").isEmpty()){
                Role role = new Role();
                role.setId(1L);
                role.setName(RolePlay.ADMIN.name());
                User user = User.builder()
                        .phoneNumber("admin")
                        .password(passwordEncoder.encode("admin"))
                        .fullName("admin")
                        .email("admin@gmail.com")
                        .address("ssss")
                        .dateOfBirth(new Date())
                        .retypePassword("admin")
                        .role(role)
                        .active(true)
                        .build();
                userRepository.save(user);
            }
        };
    }
}
