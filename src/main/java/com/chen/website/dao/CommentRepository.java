package com.chen.website.dao;

import com.chen.website.domain.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author ChenetChen
 * @since 2021/1/29 16:37
 */
public interface CommentRepository extends JpaRepository<Comment,Long> {

    List<Comment> findByBlogIdAndParentCommentNull(Long competitionId, Sort sort);
}
