package com.Nishant.LinkedIn_Mini.ConnectionService.Auth;

import com.Nishant.LinkedIn_Mini.PostService.Auth.RequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private RequestInterceptor requestInterceptor;

    //here we have to add the interceptor , but we have already create the interceptor
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestInterceptor); //it will add the this interceptor that we have created
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
