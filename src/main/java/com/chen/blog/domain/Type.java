/**
 * FileName: Type
 * Author:   嘉平十七
 * Date:     2021/6/18 10:12
 * Description: 类型实体类
 */
package com.chen.blog.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "type")
public class Type implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    //分类名
    @NotBlank(message = "分类名称不能为空")
    private String name;
    //比赛列表
    @JsonIgnore
    @OneToMany(mappedBy = "type",cascade = CascadeType.REMOVE)
    private List<Blog> blogs = new ArrayList<>();
    //创建时间
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    public Type() {
    }

    public Type(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Type{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", createTime=" + createTime +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}