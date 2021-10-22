package com.chen.website.controller.admin;

import com.chen.website.domain.Blog;
import com.chen.website.domain.BlogQuery;
import com.chen.website.domain.User;
import com.chen.website.service.BlogService;
import com.chen.website.service.TypeService;
import com.chen.website.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 后台博客管理
 * @author ChenetChen
 * @since2021/3/11 11:06
 */
@Controller
@RequestMapping("/admin")
public class AdminBlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;
    
    /**
     * 设置分类
     * @param model
     */
    private void setType(Model model){
        model.addAttribute("types",typeService.listType());
    }

    /**
     * 后台获取所有博客并分页显示
     * @param pageable
     * @param blogQuery
     * @param model
     * @return
     */
    @GetMapping("/blogs")
    public String blogs(@PageableDefault(size = 5,sort = {"createTime"},direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blogQuery, Model model){
        setType(model);
        model.addAttribute("blogs", blogService.listBlog(pageable,blogQuery));
        return "admin/blogs";
    }

    /**
     * 后台博客页面的搜索功能
     * @param pageable
     * @param blogQuery
     * @param model
     * @return
     */
    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 5,sort = {"createTime"},direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blogQuery, Model model){
        model.addAttribute("blogs", blogService.listBlog(pageable,blogQuery));
        return "admin/blogs::blogList";//返回片段
    }

    /**
     * 后台查看博客
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/blog/{id}")
    public String see(@PathVariable Long id, Model model){
        setType(model);
        model.addAttribute("blog", blogService.getAndConvert(id));
        return "admin/blog";
    }

    /**
     * 后台编辑博客
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/blog/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        setType(model);
        Blog blog = blogService.getBlogById(id);
        model.addAttribute("blog",blog);    //拿到tagIds
        return "admin/edit";
    }

    /**
     * 跳转到审核博客
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/blog/{id}/auditing")
    public String auditing(@PathVariable Long id, Model model){
        setType(model);
        Blog blog = blogService.getBlogById(id);
        model.addAttribute("blog",blog);    //拿到tagIds
        return "admin/auditing";
    }

    /**
     * 审核博客
     * @param id 博客id
     * @param n 博客审核状态
     * @return 转发到博客管理页面
     */
    @GetMapping("/blog/{id}/auditing/{n}")
    public String auditing(@PathVariable Long id, @PathVariable Integer n){
        Blog blog = blogService.getBlogById(id);
        blog.setState(n);//todo-chen 下一步错误，跳转后没有blogs，导致页面模板错误，值也没有存入
        blogService.saveBlog(blog);
        return "forward:/admin/blogs";
    }

    /**
     * 后台删除博客
     * 前端最初拼接的th:href无效，所以需要拼接href，因为th标签的解析在js之前，所以占位符也失效，抛出StringToLong的异常。
     * @param id
     * @param attributes
     * @return
     */
    @GetMapping("/blog/delete")
    public String delete(Long id, RedirectAttributes attributes){
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/blogs";
    }

    /**
     * 后台跳转到发布博客
     * @param model
     * @return
     */
    @GetMapping("/blog/input")
    public String input(Model model){
        model.addAttribute("blog",new Blog());
        setType(model);
        return "admin/edit";
    }

    /**
     * 后台发布博客
     * @param blog
     * @param attributes
     * @param session
     * @return
     */
    @PostMapping("/blog/input")
    public String post(Blog blog,
                       @RequestParam("blogPictureFile") MultipartFile blogPictureFile,
                       @RequestParam("blogPictureFileLink") String blogPictureFileLink,
                       RedirectAttributes attributes, HttpSession session){
        if (!blogPictureFile.isEmpty()) {
            try {
                blog.setPicture(FileUtils.uploadFile("blogPicture", blogPictureFile));
            } catch (IOException e) {
                System.err.println("图片文件上传失败");
                e.printStackTrace();
            }
        }else {
            blog.setPicture(blogPictureFileLink);
        }
        blog.setUser((User) session.getAttribute("adminUser"));
        blog.setType(typeService.getTypeById(blog.getType().getId()));
        blog.setState(0);
        Blog b;

        //处理view为0
        if (blog.getId() == null){
            b = blogService.saveBlog(blog);
        }else {
            b = blogService.updateBlog(blog.getId(),blog);
        }

        if (b == null){
            attributes.addFlashAttribute("message","操作失败");
        }else {
            attributes.addFlashAttribute("message","操作成功");
        }
        return "redirect:/admin/blogs";
    }

}