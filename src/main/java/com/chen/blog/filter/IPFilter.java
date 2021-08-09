/**
 * FileName: IPFilter
 * Author:   嘉平十七
 * Date:     2021/7/22 16:52
 * Description: IP过滤，超过50次/s阈值禁止访问1小时，自动解除
 */
package com.chen.blog.filter;

import com.chen.blog.utils.IPUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@WebFilter(urlPatterns = "/*")
public class IPFilter implements Filter {
    //默认限制时间（单位：毫秒）
    private static final long LIMITED_TIME_MILLIS = 60 * 30 * 1000;

    //最小安全时间（单位：毫秒）
    private static final long MIN_SAFE_TIME = 5000;

    //连续访问阈值
    private static final int LIMIT_NUMBER = 500;

    private FilterConfig config;

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.config = filterConfig;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        ServletContext context = config.getServletContext();
        // 获取IP存储器
        Map<String, Long[]> ipMap = (Map<String, Long[]>) context.getAttribute("ipMap");
        //获取限制IP存储器
        Map<String, Long> limitedIpMap = (Map<String, Long>) context.getAttribute("limitedIpMap");
        //过滤受限制的IP
        filterLimitedIpMap(limitedIpMap);
        //获取用户IP
        String ip  = IPUtils.getIpAddr(request);
        String uri = request.getRequestURI();

        if (!(uri.endsWith("js") || uri.endsWith("css") || uri.endsWith("jpg") || uri.endsWith("svg") || uri.endsWith("ico") || uri.endsWith("woff2") || uri.endsWith("png"))){
            //判断是否被限制
            if (isLimitedIp(limitedIpMap, ip)){
                long limitedTime = limitedIpMap.get(ip) - System.currentTimeMillis();
                request.setAttribute("remainingTime",((limitedTime / 1000) + (limitedTime %1000 > 0 ? 1 : 0)));
                System.err.println("IP访问频繁："+ip);
                return;
            }
            //判断IP是否存在，如果存在比较访问次数，大于阈值判断时间，超过安全时间跳到异常，不存在则视为初次访问，初始化IP
            if (ipMap.containsKey(ip)) {
                Long[] ipInfo = ipMap.get(ip);
                ipInfo[0] = ipInfo[0] + 1;
                if (ipInfo[0] > LIMIT_NUMBER) {
                    Long ipAccessTime = ipInfo[1];
                    long currentTimeMillis = System.currentTimeMillis();
                    if (currentTimeMillis - ipAccessTime <= MIN_SAFE_TIME) {
                        limitedIpMap.put(ip, currentTimeMillis + LIMITED_TIME_MILLIS);
                        request.setAttribute("remainingTime", LIMITED_TIME_MILLIS);
                        request.getRequestDispatcher("/error/ipError").forward(request, response);
                        return;
                    } else {
                        initIpVisitsNumber(ipMap, ip);
                    }
                }
            } else {
                initIpVisitsNumber(ipMap, ip);
            }
            context.setAttribute("ipMap", ipMap);
        }
        filterChain.doFilter(request, response);
    }

    /**
     * 过滤受限制IP，删除到期IP
     * @param limitedIpMap
     */
    private void filterLimitedIpMap(Map<String, Long> limitedIpMap){
        if (limitedIpMap == null){
            return;
        }
        Set<String> ipSet = limitedIpMap.keySet();
        Iterator<String> iterator = ipSet.iterator();
        long currentTimeMills = System.currentTimeMillis();
        while (iterator.hasNext()){
            long expireTimeMills = limitedIpMap.get(iterator.next());
            if (expireTimeMills <= currentTimeMills){
                iterator.remove();
            }
        }
    }

    /**
     * 判断IP是否被限制
     * @param limitedIpMap
     * @param ip
     * @return
     */
    private boolean isLimitedIp(Map<String, Long> limitedIpMap, String ip){
        if (limitedIpMap == null || ip == null){
            return false;
        }
        Set<String> ipSet = limitedIpMap.keySet();
        Iterator<String> iterator = ipSet.iterator();
        while (iterator.hasNext()){
            String key = iterator.next();
            if (key.equals(ip)){
                return true;    //被限制的IP
            }
        }
        return false;
    }

    /**
     * 初始化用户访问次数和访问时间
     * @param ipMap
     * @param ip
     */
    private void initIpVisitsNumber(Map<String, Long[]> ipMap, String ip){
        Long[] ipInfo = new Long[2];
        ipInfo[0] = 0L; //访问次数
        ipInfo[1] = System.currentTimeMillis(); //访问时间
        ipMap.put(ip, ipInfo);
    }
}