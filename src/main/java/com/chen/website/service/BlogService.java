package com.chen.website.service;

import com.chen.website.domain.Blog;
import com.chen.website.domain.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * 博客服务接口
 * @author ChenetChen
 * @since 2021/6/18 14:02
 */
public interface BlogService {

    //通过id获取博客
    Blog getBlogById(Long id);

    //通过id获取博客，在同通过工具类复制对象，用于格式转换并显示
    Blog getAndConvert(Long id);

    //列出所有博客并分页
    Page<Blog> listBlog(Pageable pageable);

    //通过条件查询博客
    Page<Blog> listBlog(Pageable pageable, BlogQuery blogQuery);

    //通过关键字查询
    Page<Blog> listBlogByQuery(String query,Pageable pageable);

    //通过分类名进行查询
    Page<Blog> listBlogByTypeName(String typeName,Pageable pageable);

    //通过浏览数查询
    List<Blog> listBlogTop(Integer size);

    //通过用户ID查询发布的博客
    List<Blog> listUserBlog(Long id);

    //发布保存博客
    Blog saveBlog(Blog blog);

    //归档处理
    Map<String, List<Blog>> archiveBlog();

    //计数
    Long countBlog();

    //更新比赛信息
    Blog updateBlog(Long id,Blog blog);

    //通过id删除比赛
    void deleteBlog(Long id);
}
