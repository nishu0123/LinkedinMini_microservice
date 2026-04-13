package com.Nishant.LinkedIn_Mini.PostService.Auth;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

//This interceptor will work with the feign client
@Component
public class FeignClientInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        Long userId = AuthContextHolder.getCurrentUserId();
        if(userId != null)
        {
            requestTemplate.header("X-User-Id" , userId.toString());
        }

    }
}
