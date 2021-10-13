package com.chen.website.controller;

import com.chen.website.domain.Blog;
import com.chen.website.domain.User;
import com.chen.website.service.BlogService;
import com.chen.website.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * 处理博客相关请求
 * @author ChenetChen
 * @since 2021/1/31 15:42
 */
@Controller
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    /**
     * 设置分类
     * @param model
     */
    private void setType(Model model) {
        model.addAttribute("types", typeService.listType());
    }

    /**
     * 前台跳转到发布博客，判断是否登录
     * @param session
     * @param model
     * @return
     */
    @GetMapping("/blog/input")
    public String input(HttpSession session, Model model) {
        if (session.getAttribute("user") == null) {
            return "login";
        }
        setType(model);
        model.addAttribute("blog", new Blog());
        return "release";
    }

    /**
     * 发布博客
     * @param blog
     * @param attributes
     * @param session
     * @return
     */
    @PostMapping("/blog/input")
    public String post(Blog blog, RedirectAttributes attributes, HttpSession session) {
        blog.setUser((User) session.getAttribute("user"));   //这里采用session中的取值
        blog.setType(typeService.getTypeById(blog.getType().getId()));
        Blog b;

        //处理view为0
        if (blog.getId() == null) {
            b = blogService.saveBlog(blog);
        } else {
            b = blogService.updateBlog(blog.getId(), blog);
        }

        if (b == null) {
            attributes.addFlashAttribute("message", "操作失败");
        } else {
            attributes.addFlashAttribute("message", "操作成功");
        }
        return "redirect:/index";
    }

    /**
     * 博客详情
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id, Model model) {
        setType(model);
        model.addAttribute("blog", blogService.getAndConvert(id));
        return "blog";
    }

    /**
     * 列出用户的发布的博客
     * @param userId
     * @param model
     * @return
     */
    @GetMapping("/user/userBlogs")
    public String userBlogs(Long userId, Model model) {
        model.addAttribute("blogs", blogService.listUserBlog(userId));
        return "userInfo::blogList";
    }
}