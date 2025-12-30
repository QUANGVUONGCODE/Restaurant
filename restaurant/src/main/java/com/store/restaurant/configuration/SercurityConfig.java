package com.store.restaurant.configuration;

import com.store.restaurant.enums.RolePlay;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SercurityConfig {

    private static final String api = "/api/v1";


    private final String[] publicEntrypoint = {
            api + "/auth/log-in",
            api +"/auth/introspect",
            api +"/auth/refresh",
            api +"/auth/logout"
    };


    CustomJwtDecoder jwtDecoder;

    private static final String blogEntryPoint = "/blog";
    private static final String foodEntryPoint = "/foods";
    private static final String categoryEntryPoint = "/categories";
    private static final String commentEntryPoint = "/comment";
    private static final String sectionEntryPoint = "/sections";
    private static final String paymentEntryPoint = "/payments";
    private static final String userEntryPoint = "/user";
    private static final String tableEntryPoint = "/tables";
    private static final String orderEntryPoint = "/order";
    private static final String orderDetailEntryPoint = "/order-details";

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(request -> request
                        .requestMatchers(HttpMethod.POST, publicEntrypoint)
                        .permitAll()

                        //blog
                        .requestMatchers(HttpMethod.GET, api + blogEntryPoint + "/**")
                        .permitAll()
                        .requestMatchers(HttpMethod.POST, api +  blogEntryPoint)
                        .hasRole(RolePlay.ADMIN.name())
                        .requestMatchers(HttpMethod.GET, api + blogEntryPoint +"{id}")
                        .permitAll()
                        .requestMatchers(HttpMethod.POST, api + blogEntryPoint + "/upload/" + "{id}")
                        .hasRole(RolePlay.ADMIN.name())
                        .requestMatchers(HttpMethod.GET, api + blogEntryPoint + "/images/" + "{filename}")
                        .permitAll()
                        .requestMatchers(HttpMethod.PUT, api + blogEntryPoint + "/{id}")
                        .hasRole(RolePlay.ADMIN.name())
                        .requestMatchers(HttpMethod.DELETE, api + blogEntryPoint + "/{id}")
                        .hasRole(RolePlay.ADMIN.name())


                        //food
                        .requestMatchers(HttpMethod.GET, api + foodEntryPoint + "/{id}")
                        .permitAll()
                        .requestMatchers(HttpMethod.GET, api + foodEntryPoint + "/images/" + "{filename}")
                        .permitAll()
                        .requestMatchers(HttpMethod.GET, api + foodEntryPoint + "/imageFoods/" + "{foodId}")
                        .permitAll()
                        .requestMatchers(HttpMethod.POST, api + foodEntryPoint + "/upload/" + "{id}")
                        .hasRole(RolePlay.ADMIN.name())
                        .requestMatchers(HttpMethod.PUT, api + foodEntryPoint + "/{id}")
                        .hasRole(RolePlay.ADMIN.name())
                        .requestMatchers(HttpMethod.DELETE, api + foodEntryPoint + "/{id}")
                        .hasRole(RolePlay.ADMIN.name())
                        .requestMatchers(HttpMethod.POST, api + foodEntryPoint)
                        .hasRole(RolePlay.ADMIN.name())
                        .requestMatchers(HttpMethod.GET, api + foodEntryPoint)
                        .permitAll()
                        .requestMatchers(HttpMethod.GET, api + foodEntryPoint + "/count")
                        .hasRole(RolePlay.ADMIN.name())

                        //Category
                        .requestMatchers(HttpMethod.GET, api + categoryEntryPoint)
                        .permitAll()
                        .requestMatchers(HttpMethod.POST, api + categoryEntryPoint)
                        .hasRole(RolePlay.ADMIN.name())
                        .requestMatchers(HttpMethod.GET, api + categoryEntryPoint +"/{id}")
                        .permitAll()
                        .requestMatchers(HttpMethod.PUT, api + categoryEntryPoint +"/{id}")
                        .hasRole(RolePlay.ADMIN.name())
                        .requestMatchers(HttpMethod.DELETE, api + categoryEntryPoint +"/{id}")
                        .hasRole(RolePlay.ADMIN.name())
                        .requestMatchers(HttpMethod.GET, api + categoryEntryPoint +"/count")
                        .hasRole(RolePlay.ADMIN.name())

                        //Comment
                        .requestMatchers(HttpMethod.GET, api + commentEntryPoint)
                        .permitAll()
                        .requestMatchers(HttpMethod.POST, api + commentEntryPoint)
                        .hasRole(RolePlay.USER.name())



                        //Section
                        .requestMatchers(HttpMethod.POST, api + sectionEntryPoint)
                        .hasRole(RolePlay.ADMIN.name())
                        .requestMatchers(HttpMethod.GET, api + sectionEntryPoint)
                        .permitAll()
                        .requestMatchers(HttpMethod.GET, api + sectionEntryPoint +"/{id}")
                        .permitAll()
                        .requestMatchers(HttpMethod.PUT, api + sectionEntryPoint +"/{id}")
                        .hasRole(RolePlay.ADMIN.name())
                        .requestMatchers(HttpMethod.DELETE, api + sectionEntryPoint +"/{id}")
                        .hasRole(RolePlay.ADMIN.name())

                        //Payment
                        .requestMatchers(HttpMethod.POST, api + paymentEntryPoint)
                        .hasRole(RolePlay.ADMIN.name())
                        .requestMatchers(HttpMethod.GET, api + paymentEntryPoint)
                        .permitAll()
                        .requestMatchers(HttpMethod.GET, api + paymentEntryPoint +"/{id}")
                        .permitAll()
                        .requestMatchers(HttpMethod.PUT, api + paymentEntryPoint +"/{id}")
                        .hasRole(RolePlay.ADMIN.name())
                        .requestMatchers(HttpMethod.DELETE, api + paymentEntryPoint +"/{id}")
                        .hasRole(RolePlay.ADMIN.name())

                        .requestMatchers(HttpMethod.POST, api + paymentEntryPoint +"/create_payment_url")
                        .hasAnyRole(RolePlay.ADMIN.name(), RolePlay.USER.name())
                        .requestMatchers(HttpMethod.POST, api + paymentEntryPoint +"/refund")
                        .hasAnyRole(RolePlay.ADMIN.name(), RolePlay.USER.name())
                        .requestMatchers(HttpMethod.POST, api + paymentEntryPoint +"/query")
                        .hasAnyRole(RolePlay.ADMIN.name(), RolePlay.USER.name())
                        .requestMatchers(HttpMethod.POST, api + paymentEntryPoint + "/result/{txnRef}")
                        .hasAnyRole(RolePlay.ADMIN.name(), RolePlay.USER.name())

                        //user
                        .requestMatchers(HttpMethod.POST, api + userEntryPoint)
                        .permitAll()
                        .requestMatchers(HttpMethod.GET, api + userEntryPoint)
                        .hasAnyRole(RolePlay.ADMIN.name())
                        .requestMatchers(HttpMethod.GET, api + userEntryPoint + "/myInfo")
                        .hasAnyRole(RolePlay.ADMIN.name(), RolePlay.USER.name())
                        .requestMatchers(HttpMethod.PUT, api + userEntryPoint + "/{id}")
                        .hasAnyRole(RolePlay.USER.name())
                        .requestMatchers(HttpMethod.DELETE, api + userEntryPoint + "/{id}")
                        .hasAnyRole(RolePlay.USER.name())

                        //tables
                        .requestMatchers(HttpMethod.GET, api + tableEntryPoint + "/**")
                        .permitAll()
                        .requestMatchers(HttpMethod.POST, api + tableEntryPoint)
                        .hasRole(RolePlay.ADMIN.name())
                        .requestMatchers(HttpMethod.PUT, api + tableEntryPoint + "/{id}")
                        .hasRole(RolePlay.ADMIN.name())
                        .requestMatchers(HttpMethod.DELETE, api + tableEntryPoint + "/{id}")
                        .hasRole(RolePlay.ADMIN.name())


                        //order
                        .requestMatchers(HttpMethod.GET, api + orderEntryPoint + "/**")
                        .hasAnyRole(RolePlay.ADMIN.name(), RolePlay.USER.name())
                        .requestMatchers(HttpMethod.POST, api + orderEntryPoint)
                        .hasAnyRole(RolePlay.ADMIN.name(), RolePlay.USER.name())
                        .requestMatchers(HttpMethod.PUT, api + orderEntryPoint + "/{id}")
                        .hasAnyRole(RolePlay.ADMIN.name(), RolePlay.USER.name())
                        .requestMatchers(HttpMethod.DELETE, api + orderEntryPoint + "/{id}")
                        .hasAnyRole(RolePlay.ADMIN.name(), RolePlay.USER.name())
                        .requestMatchers(HttpMethod.GET, api + orderEntryPoint + "/total-revenue")
                        .hasRole(RolePlay.ADMIN.name())
                        .requestMatchers(HttpMethod.PUT , api + orderEntryPoint + "/status/{id}")
                        .hasRole(RolePlay.ADMIN.name())
                        .requestMatchers(HttpMethod.GET, api + orderDetailEntryPoint + "/**")
                        .hasAnyRole(RolePlay.ADMIN.name(), RolePlay.USER.name())
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwtConfigurer -> jwtConfigurer
                                .decoder(jwtDecoder)
                                .jwtAuthenticationConverter(jwtAuthenticationConverter())
                        )
                        .authenticationEntryPoint(new JwtAuthenticationEntryPoint())
                )
                .csrf(AbstractHttpConfigurer::disable);
        return httpSecurity.build();
    }

    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter(){
        JwtGrantedAuthoritiesConverter converter = new JwtGrantedAuthoritiesConverter();
        converter.setAuthorityPrefix("");
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(converter);
        return jwtAuthenticationConverter;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("http://localhost:3000");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }


    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(10);
    }

}
