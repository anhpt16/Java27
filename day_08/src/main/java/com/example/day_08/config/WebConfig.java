package com.example.day_08.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private final UserInterceptor userInterceptor;
    private final AuthenticationInterceptor authenticationInterceptor;
    private final AuthorizationInterceptor authorizationInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userInterceptor)
            .addPathPatterns("/**");

        registry.addInterceptor(authenticationInterceptor)
            .addPathPatterns(
                "/api/reviews", "/api/reviews/**",
                "/phim-yeu-thich", "/api/favorites", "api/favorites/**");

//        registry.addInterceptor(authorizationInterceptor)
//            .addPathPatterns("/api/admin/**", "/admin/**");
    }
}
