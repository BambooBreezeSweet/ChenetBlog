package com.chen.website.dao;

import com.chen.website.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ChenetChen
 * @since 2021/1/29 16:32
 */
@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    /**
     * 通过用户名查询，用于注册检验重名
     * @param username
     * @return
     */
    User findByUsername(String username);

    /**
     * 通过邮箱查询，用户注册检验
     * @param email
     * @return
     */
    User findByEmail(String email);

    /**
     * 通过用户名和密码查询
     * @param username
     * @param password
     * @return
     */
    User findByUsernameAndPassword(String username,String password);

    /**
     * 通过邮箱和密码查询
     * @param email
     * @param password
     * @return
     */
    User findByEmailAndPassword(String email,String password);

    /**
     * 用户名模糊查询
     * @param query
     * @param pageable
     * @return
     */
    @Query("select u from User u where u.username like ?1")
    Page<User> findByQuery(String query, Pageable pageable);

    /**
     * 更新用户信息操作
     * @param id
     * @param username
     * @param email
     * @param avatar
     * @param description
     */
    @Transactional
    @Modifying //通知springData是更新或修改操作
    @Query("update User u set u.username = ?2,u.email = ?3,u.avatar = ?4,u.description = ?5 where u.id = ?1")
    void updateUser(Long id, String username, String email, Integer avatar, String description);

    @Transactional
    @Modifying
    @Query("update User u set u.role = 1 where u.id = ?1")
    void upgradeRole(Long id);

    @Transactional
    @Modifying
    @Query("update User u set u.role = 0 where u.id = ?1")
    void demotionRole(Long id);
}