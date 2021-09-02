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
        // 获取页面手动传递的语言参数值lang: zh_CN  en_US
        String l = httpServletRequest.getParameter("lang");
        Locale locale = null;
        if(!StringUtils.isEmpty(l)){
            // 如果参数不为空，就根据参数值进行手动语言切换
            String[] s = l.split("_");
            locale = new Locale(s[0],s[1]);
        }else {
            //Accept-Language: zh-CN ,zh;q=0.9
            String header = httpServletRequest.getHeader("Accept-Language");
            String[] split = header.split(",");
            String[] split1 = split[0].split("-");
            locale = new Locale(split1[0],split1[1]);
        }
        return locale;
    }

    @Override
    public void setLocale(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Locale locale) {

    }
}