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

    User findByUsername(String username);

    User findByEmail(String email);

    User findByUsernameAndPassword(String username,String password);

    User findByEmailAndPassword(String email,String password);

    @Query("select u from User u where u.username like ?1")
    Page<User> findByQuery(String query, Pageable pageable);

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

    @Transactional
    @Modifying
    @Query("update User u set u.state = 1 where u.id = ?1")
    void deleteUserById(Long id);

    @Transactional
    @Modifying
    @Query("update User u set u.state = 0 where u.id = ?1")
    void regainUser(Long id);
}