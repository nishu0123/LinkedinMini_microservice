package com.Nishant.LinkedIn_Mini.UserService.Auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RequestInterceptor implements HandlerInterceptor {

    //this will be called before request goes to the controller , so here we can handle the user-id
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        String userIdHeader = request.getHeader("X-USER-ID");

        if (userIdHeader == null || userIdHeader.isBlank()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Missing X-USER-ID header");
            return false;
        }

        try {
            Long userId = Long.valueOf(userIdHeader);
            AuthContextHolder.setCurrentUserId(userId);
        } catch (NumberFormatException ex) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Invalid X-USER-ID header");
            return false;
        }

        return true;
    }


    //it is called after we got the response , of exception occurs , this function is mainly used to
    //remove the user id
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        AuthContextHolder.clear(); // remove the userId , otherwise there will memory leak in thread pool
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }


}
