package com.chen.website.service;

import com.chen.website.domain.Comment;

import java.util.List;

/**
 * 评论服务接口
 * @author ChenetChen
 * @since 2021/6/18 14:00
 */
public interface CommentService {

    //通过比赛id查询对应的评论列表
    List<Comment> listCommentByBlogId(Long competitionId);

    //保存评论
    Comment saveComment(Comment comment);

    //删除评论
    void deleteComment(Long id);
}
