package com.chen.website.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 博客实体类
 * @author ChenetChen
 * @since 2021/6/18 10:05
 */
@Entity
@Table(name = "blog")
public class Blog implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    //博客标题
    private String title;
    //博客图片
    private String picture;
    //博客内容
    @Lob    //指定持久属性或字段应作为大对象持久保存到数据库支持的大对象类型
    @Basic(fetch = FetchType.LAZY)  //懒加载
    private String content;
    //博客浏览量
    private Integer views;
    //博客描述
    private String description;
    //创建时间
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    //博客审核状态，0为未审核
    private Integer state;
    //发布人
    @JsonIgnore
    @ManyToOne
    private User user;
    //所属分类
    @JsonIgnore
    @ManyToOne
    private Type type;
    //博客评论
    @JsonIgnore
    @OneToMany(mappedBy = "blog",cascade = CascadeType.REMOVE)
    private List<Comment> comments = new ArrayList<>();

    public Blog() {
    }

    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", picture='" + picture + '\'' +
                ", content='" + content + '\'' +
                ", views=" + views +
                ", description='" + description + '\'' +
                ", createTime=" + createTime +
                ", state=" + state +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}