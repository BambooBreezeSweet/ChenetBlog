package com.chen.website.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 博客详情页拦截
 * @author ChenetChen
 * @since 2021/6/18 13:52
 */
public class BlogInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getSession().getAttribute("user") == null){
            response.sendRedirect("/toLogin");
            return false;
        }
        return true;
    }
}