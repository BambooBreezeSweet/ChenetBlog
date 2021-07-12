/**
 * FileName: MvcConfig
 * Author:   嘉平十七
 * Date:     2021/6/18 13:51
 * Description:
 */
package com.chen.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/index.html").setViewName("index");
    }

    //区域信息解析器手动创建会覆盖系统自动的，所以自己添加到容器中
    @Bean
    public LocaleResolver localeResolver(){
        return new com.chen.blog.config.MyLocaleResolverConfig();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/upload/images/**")
                .addResourceLocations("file:"+System.getProperty("user.dir")+"\\src\\main\\resources\\static\\upload\\images\\");
    }
}