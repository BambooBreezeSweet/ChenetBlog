package com.chen.website.controller;

import com.chen.website.service.BlogService;
import com.chen.website.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 处理分类相关的请求
 * @author ChenetChen
 * @since 2021/6/18 14:35
 */
@Controller
public class TypeController {

    @Autowired
    private BlogService blogService;
    
    @Autowired
    private TypeService typeService;

    /**
     * 前台分类
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping("/types")
    public String types(@PageableDefault(size = 5,sort = {"id"},direction = Sort.Direction.ASC) Pageable pageable, Model model){
        model.addAttribute("types",typeService.listType(pageable));
        model.addAttribute("blogs", blogService.listBlog(pageable));
        return "types";
    }
}