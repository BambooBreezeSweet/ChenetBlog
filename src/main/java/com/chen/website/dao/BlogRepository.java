package com.chen.website.dao;

import com.chen.website.domain.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ChenetChen
 * @since 2021/1/29 16:36
 */
public interface BlogRepository extends JpaRepository<Blog,Long>, JpaSpecificationExecutor<Blog> {

    @Query("select b from Blog b where b.state = 1")
    Page<Blog> findAllBlog(Pageable pageable);

    /**
     * 更新浏览次数
     * @param id
     * @return
     */
    @Transactional
    @Modifying
    @Query("update Blog b set b.views = b.views+1 where b.id = ?1")
    int updateViews(Long id);

    /**
     * 通过浏览数排序查询热门博客
     * @param pageable
     * @return
     */
    @Query("select b from Blog b where b.state = 1 order by b.views desc")
    List<Blog> findTop(Pageable pageable);

    /**
     * 关键字查询，可以查询用户名、标题、内容中的关键字
     * @param query
     * @param pageable
     * @return
     */
    @Query("select b from Blog b where b.title like ?1 or b.content like ?1 or b.user.username like ?1 and b.state = 1")
    Page<Blog> findBlogByQuery(String query, Pageable pageable);

    /**
     * 分类查询
     * @param query
     * @param pageable
     * @return
     */
    @Query("select b from Blog b where b.type.name like ?1 and b.state = 1")
    Page<Blog> findBlogByTypeName(String query, Pageable pageable);

    /**
     * 通过年份归档
     * @return
     */
    @Query("select function('date_format',b.createTime,'%Y') as year from Blog b where b.state = 1 group by function('date_format',b.createTime,'%Y') order by function('date_format',b.createTime,'%Y') desc")
    List<String> findBlogsByGroupYears();

    /**
     * 归档查询
     * @param year
     * @return
     */
    @Query("select b from Blog b where function('date_format',b.createTime,'%Y') = ?1 and b.state = 1")
    List<Blog> findBlogsByYear(String year);

    /**
     * 查询用户发布的博客
     * SQL语句：@Query(value = "select * from Blog inner join user u on Blog.user_id = u.id where u.id = ?1",nativeQuery = true)
     * @param userId
     * @return
     */
    @Query("select b from Blog b where b.user.id = ?1 and b.state = 1")
    List<Blog> findBlogByUserId(Long userId);

    @Query("select count(b) from Blog b where b.state = 1")
    long countBlogs();

}
