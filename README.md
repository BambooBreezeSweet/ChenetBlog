# 竹风博客——个人博客网站
![image-001](capture/项目示例.jpg)

## 项目说明

### 介绍

**个人博客网站**

> 地址：http://www.chenaichenet.ltd

> 项目相关博客：

该系统采用IntelliJ IDEA，使用Spring Boot框架基于MVC框架进行开发，使用MySQL数据库，利用HTML，JavaScript，Ajax，Thymeleaf模板引擎和Semantic UI完成界面设计。

### 技术框架及环境
- IntelliJ IDEA 2021.1
- Spring Boot 2.4.2
- MySQL 8.0.21
- Redis 3.2.100
- Spring Data JPA
- Hibernate
- Spring Security
- Thymeleaf
- Semantic UI
- Editor.md
- Tocbot.js
- QRCode.js
- Waypoint.js

### 目录说明
```text
src/main/java/com/chen/blog
- aspect                  日志配置
- config                  配置类
- controller              控制层
- dao                     数据访问层
- domain                  实体类
- handler                 异常处理
- interceptor             拦截过滤
- service                 业务层
- utils                   工具类
BlogApplication           启动类

src/main/resources
- i18n                    国际化资源
- static                  静态资源
- templates               页面模板
application.yml           配置文件

logs                      日志文件
```

###相关资源

[Semantic UI官网](https://semantic-ui.com/)

[Semantic UI中文官网](http://www.semantic-ui.cn/)

[编辑器 Markdown](https://pandao.github.io/editor.md/)

[内容排版 typo.css](https://github.com/sofish/typo.css)

[目录生成 Tocbot](https://tscanlin.github.io/tocbot/)

[滚动侦测 waypoints](http://imakewebthings.com/waypoints/)

[平滑滚动 jquery.scrollTo](https://github.com/flesler/jquery.scrollTo)

[二维码生成 qrcode.js](https://davidshimjs.github.io/qrcodejs/)