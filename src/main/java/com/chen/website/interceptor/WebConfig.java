package com.chen.website.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 拦截配置
 * @author ChenetChen
 * @since 2021/6/18 13:53
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //管理员
        registry.addInterceptor(new AdminLoginInterceptor())
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin")
                .excludePathPatterns("/admin/login");
        //用户页面
        registry.addInterceptor(new UserInterceptor())
                .addPathPatterns("/user/**")
                .excludePathPatterns("/user/verCode");
        //比赛详情页面
        registry.addInterceptor(new BlogInterceptor())
                .addPathPatterns("/blog/**");
    }
}