package com.poly.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

import com.poly.entity.User;
import com.poly.exception.UnauthorizedAccessException;

public class AuthenticationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("/login");
            return false;
        }else if(user.getRole().equalsIgnoreCase("user")) {
            response.sendRedirect("/role");
            return false;
        }else if (user.getRole().equalsIgnoreCase("staff")) {
            String requestURI = request.getRequestURI();
            if (requestURI.contains("/adm/thongke")) {
                response.sendRedirect("/role");
                return false;
            }
        }
        return true;
    }
}
