/**
 * FileName: Comment
 * Author:   嘉平十七
 * Date:     2021/6/18 10:15
 * Description:
 */
package com.chen.webiste.domain;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue
    private Long id;
    //用户
    @ManyToOne
    private User user;
    //评论内容
    private String content;
    //是否博主评论
    private boolean adminComment;
    //评论时间
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    //评论所属博客
    @ManyToOne
    private Blog blog;
    //回复评论
    @OneToMany(mappedBy = "parentComment",fetch = FetchType.EAGER,cascade = CascadeType.REMOVE)
    @Cascade(value = {org.hibernate.annotations.CascadeType.REMOVE})
    private List<Comment> replyComments = new ArrayList<>();
    //父评论
    @ManyToOne
    private Comment parentComment;

    public Comment() {
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", adminComment=" + adminComment +
                ", createTime=" + createTime +
                ", replyComments=" + replyComments +
                ", parentComment=" + parentComment +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isAdminComment() {
        return adminComment;
    }

    public void setAdminComment(boolean adminComment) {
        this.adminComment = adminComment;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    public List<Comment> getReplyComments() {
        return replyComments;
    }

    public void setReplyComments(List<Comment> replyComments) {
        this.replyComments = replyComments;
    }

    public Comment getParentComment() {
        return parentComment;
    }

    public void setParentComment(Comment parentComment) {
        this.parentComment = parentComment;
    }
}