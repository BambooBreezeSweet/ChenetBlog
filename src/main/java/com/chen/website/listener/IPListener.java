package com.chen.website.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.HashMap;
import java.util.Map;

/**
 * IP监听
 * @author ChenetChen
 * @since 2021/7/22 17:41
 */
@WebListener
public class IPListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.err.println("IPListener初始化成功");
        ServletContext context = sce.getServletContext();
        // IP存储器
        Map<String, Long[]> ipMap = new HashMap<String, Long[]>();
        context.setAttribute("ipMap", ipMap);
        // 限制IP存储器：存储被限制的IP信息
        Map<String, Long> limitedIpMap = new HashMap<String, Long>();
        context.setAttribute("limitedIpMap", limitedIpMap);
    }
}