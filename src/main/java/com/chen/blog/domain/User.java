/**
 * FileName: User
 * Author:   嘉平十七
 * Date:     2021/6/18 9:54
 * Description: 用户实体类
 */
package com.chen.blog.domain;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue
    private Long id;
    //用户名
    private String username;
    //密码
    private String password;
    //头像
    private Integer avatar;
    //邮箱
    private String email;
    //描述
    private String description;
    //创建时间
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    //角色
    private Integer role;
    //发布的博客
    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER,cascade = CascadeType.REMOVE)
    @Cascade(value = {org.hibernate.annotations.CascadeType.REMOVE})
    private List<Blog> blogs = new ArrayList<>();

    public User() {
    }

    public User(String username, String password, Integer avatar, Integer role, String email, Date createTime) {
        this.username = username;
        this.password = password;
        this.avatar = avatar;
        this.role = role;
        this.email = email;
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", avatar=" + avatar +
                ", email='" + email + '\'' +
                ", description='" + description + '\'' +
                ", createTime=" + createTime +
                ", role=" + role +
                ", blogs=" + blogs +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAvatar() {
        return avatar;
    }

    public void setAvatar(Integer avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public List<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }
}