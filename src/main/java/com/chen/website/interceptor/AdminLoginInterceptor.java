package com.chen.website.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 管理员页面拦截
 * @author ChenetChen
 * @since 2021/6/18 13:52
 */
public class AdminLoginInterceptor implements HandlerInterceptor {
    /**
     * 方法前调用
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        //判断用户是否登录
        if (request.getSession().getAttribute("adminUser") == null){
            response.sendRedirect("/admin");
            return false;
        }
        return true;
    }
}