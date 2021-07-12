/**
 * FileName: CommentRepository
 * Author:   嘉平十七
 * Date:     2021/1/29 16:37
 * Description:
 */
package com.chen.blog.dao;

import com.chen.blog.domain.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {

    List<Comment> findByBlogIdAndParentCommentNull(Long competitionId, Sort sort);
}
