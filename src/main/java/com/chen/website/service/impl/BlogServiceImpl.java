package com.chen.website.service.impl;

import com.chen.website.dao.BlogRepository;
import com.chen.website.domain.Blog;
import com.chen.website.domain.BlogQuery;
import com.chen.website.domain.Type;
import com.chen.website.service.BlogService;
import com.chen.website.utils.MarkdownUtils;
import com.chen.website.utils.MyBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.*;

/**
 * @author ChenetChen
 * @since 2021/6/18 14:04
 */
@Service
public class BlogServiceImpl implements BlogService {
    @Resource
    private BlogRepository blogRepository;

    @Override
    public Blog getBlogById(Long id) {
        Optional<Blog> blogStatus = blogRepository.findById(id);
        return blogStatus.orElse(null);
    }

    /**
     * 通过id获取博客，通过工具类复制对象，用于转换格式并显示
     * @param id
     * @return
     */
    @Transactional
    @Override
    public Blog getAndConvert(Long id) {
        Blog blog = blogRepository.getById(id);
        Blog newBlog = new Blog();
        BeanUtils.copyProperties(blog,newBlog);
        String content = newBlog.getContent();
        newBlog.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        blogRepository.updateViews(id);
        return newBlog;
    }

    /**
     * 列出所有博客并分页
     * @param pageable
     * @return
     */
    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        return blogRepository.findAllBlog(pageable);
    }

    /**
     * 通过条件进行查询博客
     * @param pageable
     * @param blogQuery
     * @return
     */
    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blogQuery) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                /*title查询*/
                if (!"".equals(blogQuery.getTitle()) && blogQuery.getTitle()!=null){
                    predicates.add(criteriaBuilder.like(root.get("title"),"%"+blogQuery.getTitle()+"%"));
                }
                /*分类查询*/
                if (blogQuery.getTypeId()!=null){
                    predicates.add(criteriaBuilder.equal(root.<Type>get("type").get("id"),blogQuery.getTypeId()));
                }
                criteriaQuery.where(predicates.toArray(new Predicate[0]));
                return null;
            }
        },pageable);
    }

    /**
     * 关键字查询并分页处理
     * @param query 关键字
     * @param pageable 分页
     * @return
     */
    @Override
    public Page<Blog> listBlogByQuery(String query, Pageable pageable) {
        return blogRepository.findBlogByQuery(query, pageable);
    }

    /**
     * 通过分类名进行查询并分页
     * @param typeName 分类名
     * @param pageable 分页处理
     * @return
     */
    @Override
    public Page<Blog> listBlogByTypeName(String typeName, Pageable pageable) {
        return blogRepository.findBlogByTypeName(typeName, pageable);
    }

    /**
     * 列出几个热门博客
     * @param size 排名的个数
     * @return
     */
    @Override
    public List<Blog> listBlogTop(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC,"views");
        Pageable pageable = PageRequest.of(0,size,sort);
        return blogRepository.findTop(pageable);
    }

    /**
     * 列出用户发布的博客
     * @param id
     * @return
     */
    @Override
    public List<Blog> listUserBlog(Long id) {
        return blogRepository.findBlogByUserId(id);
    }

    /**
     * 发布博客
     * @param blog
     * @return
     */
    @Override
    public Blog saveBlog(Blog blog) {
        if (blog.getId() == null){
            blog.setCreateTime(new Date());
            blog.setViews(0);
        }
        return blogRepository.save(blog);
    }

    /**
     * 归档
     * @return
     */
    @Override
    public Map<String, List<Blog>> archiveBlog() {
        List<String> years = blogRepository.findBlogsByGroupYears();
        Map<String,List<Blog>> map = new HashMap<>();
        for (String year : years){
            map.put(year,blogRepository.findBlogsByYear(year));
        }
        return map;
    }

    /**
     * 统计博客数
     * @return
     */
    @Override
    public Long countBlog() {
        return blogRepository.countBlogs();
    }

    /**
     * 更新博客信息
     * @param id
     * @param blog
     * @return
     */
    @Transactional
    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog blogCopy = blogRepository.getById(id);
        BeanUtils.copyProperties(blog,blogCopy, MyBeanUtils.getNullPropertyNames(blog));
        blogCopy.setCreateTime(new Date());
        return blogRepository.save(blogCopy);
    }

    /**
     * 删除博客
     * @param id
     */
    @Override
    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }
}