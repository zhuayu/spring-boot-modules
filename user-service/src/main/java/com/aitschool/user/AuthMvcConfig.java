package com.aitschool.user;

import com.aitschool.common.interceptor.AuthWebInterceptor;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AuthMvcConfig implements WebMvcConfigurer {

    @Resource
    AuthWebInterceptor authWebInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authWebInterceptor).addPathPatterns("/web/**");
    }
}
