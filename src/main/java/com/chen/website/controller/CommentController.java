package com.chen.website.controller;

import com.chen.website.domain.Comment;
import com.chen.website.domain.User;
import com.chen.website.service.BlogService;
import com.chen.website.service.CommentService;
import com.chen.website.utils.BadWordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

/**
 * 前台比赛详情中的评论请求处理
 * @author ChenetChen
 * @since 2021/1/28 10:31
 */
@Controller
public class CommentController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private CommentService commentService;

    /**
     * 通过博客Id获取对应的评论列表
     * @param blogId 比赛ID
     * @param model
     * @return
     */
    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable Long blogId, Model model){
        model.addAttribute("comments",commentService.listCommentByBlogId(blogId));
        return "blog :: commentList";
    }

    /**
     * 发布评论
     * @param comment
     * @param session
     * @return
     */
    @PostMapping("/comment")
    public String post(Comment comment, HttpSession session){
        Long blogId = comment.getBlog().getId();
        comment.setBlog(blogService.getBlogById(blogId));
        User user = (User) session.getAttribute("user"); //当前登录用户
        if (user == null){
            return "login";
        }else if (user.getId() == comment.getBlog().getUser().getId()){
            comment.setUser(user);
            comment.setAdminComment(true);
        }else {
            comment.setUser(user);
        }
        String content = BadWordUtils.filter(comment.getContent());
        comment.setContent(content);
        commentService.saveComment(comment);
        return "redirect:/comments/"+blogId;
    }

    @PostMapping ("/comment/delete")
    public String deleteComment(Long blogId,Long commentId){
        commentService.deleteComment(commentId);
        return "redirect:/comments/"+blogId;
    }

}