/**
 * FileName: Notice
 * Author:   嘉平十七
 * Date:     2021/8/17 17:28
 * Description: 公告实体类
 */
package com.chen.website.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "notice")
public class Notice {
    @Id
    @GeneratedValue
    private Long id;

    private String title;

    @Lob    //指定持久属性或字段应作为大对象持久保存到数据库支持的大对象类型
    @Basic(fetch = FetchType.LAZY)  //懒加载
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    private String description;

    public Notice() {
    }

    public Notice(Long id, String title, String content, Date createTime, String description) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createTime = createTime;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Notice{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", createTime=" + createTime +
                ", description='" + description + '\'' +
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}