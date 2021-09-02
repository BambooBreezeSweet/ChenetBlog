package com.chen.website.service;

import com.chen.website.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 用户服务接口
 * @author ChenetChen
 * @since 2021/6/18 10:29
 */
public interface UserService {
    //列出所有用户并分页
    Page<User> listUsers(Pageable pageable);
    //用户注册
    User addUser(User newUser);
    //用户名登录
    User checkUserByUsername(String username,String password);
    //邮箱登录
    User checkUserByEmail(String email,String password);
    //通过ID查询
    User findUserById(Long id);
    //通过用户名查询
    User findUserByUsername(String username);
    //邮箱查询
    User findUserByEmail(String email);
    //关键字查询
    Page<User> findUserByQuery(String query,Pageable pageable);
    //更新用户信息
    String updateUser(User user);
    //更新权限
    String upgradeRole(Long id);
    String demotionRole(Long id);
    //删除用户
    void deleteUser(Long id);
}
