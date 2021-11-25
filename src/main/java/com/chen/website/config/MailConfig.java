package com.chen.website.config;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import org.springframework.stereotype.Component;

/**
 * MailUtil工具类
 * @author ChenetChen
 * @since 2021/6/18 13:50
 */
@Component
public class MailConfig {
    @NacosValue(value = "${spring.mail.host}", autoRefreshed = true)
    private String mailHost;
    @NacosValue(value = "${spring.mail.username}", autoRefreshed = true)
    private String username;
    @NacosValue(value = "${spring.mail.password}", autoRefreshed = true)
    private String password;
    @NacosValue(value = "${spring.mail.templatePath}", autoRefreshed = true)
    private String TemplatesPath;


    public String getMailHost() {
        return mailHost;
    }

    public void setMailHost(String mailHost) {
        this.mailHost = mailHost;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTemplatesPath() {
        return TemplatesPath;
    }

    public void setTemplatesPath(String templatesPath) {
        TemplatesPath = templatesPath;
    }
}