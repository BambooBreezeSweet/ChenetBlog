/**
 * FileName: CommentService
 * Author:   嘉平十七
 * Date:     2021/6/18 14:00
 * Description: 评论服务类
 */
package com.chen.website.service;

import com.chen.website.domain.Comment;

import java.util.List;

public interface CommentService {

    //通过比赛id查询对应的评论列表
    List<Comment> listCommentByBlogId(Long competitionId);

    //保存评论
    Comment saveComment(Comment comment);

    //删除评论
    void deleteComment(Long id);
}
