package com.chen.website.config;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * 语言环境解析配置类
 * @author ChenetChen
 * @since 2021/6/18 13:51
 */
public class MyLocaleResolverConfig implements LocaleResolver {

    @Override
    public Locale resolveLocale(HttpServletRequest httpServletRequest) {
        String l = httpServletRequest.getParameter("l");
        Locale locale = Locale.getDefault();    //默认的
        if (!StringUtils.isEmpty(l)){
            String[] split = l.split("_");//分割zh和CN
            locale=new Locale(split[0],split[1]);//一个语言，一个国家
        }
        return locale;
    }

    @Override
    public void setLocale(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Locale locale) {

    }
}