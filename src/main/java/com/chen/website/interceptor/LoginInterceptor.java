package com.chen.website.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 前台用户登录拦截
 *
 * @author ChenetChen
 * @since 2021/11/4 14:07
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        if (request.getSession().getAttribute("user") == null){
            response.sendRedirect("/toLogin");
            return false;
        }
        return true;
    }
}