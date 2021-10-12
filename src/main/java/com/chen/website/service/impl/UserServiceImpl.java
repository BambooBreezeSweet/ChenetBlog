package com.chen.website.service.impl;

import com.chen.website.dao.UserRepository;
import com.chen.website.domain.User;
import com.chen.website.service.UserService;
import com.chen.website.utils.MyBeanUtils;
import com.chen.website.utils.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * @author ChenetChen
 * @since 2021/6/18 10:51
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    //列出所有用户并分页
    @Override
    public Page<User> listUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    //用户注册
    @Override
    public User addUser(User newUser) {
        User user1 = userRepository.findByUsername(newUser.getUsername());
        User user2 = userRepository.findByEmail(newUser.getEmail());
        if (user1 == null && user2 == null){
            return userRepository.save(newUser);
        }else {
            throw new NotFoundException("用户名或邮箱已经注册");
        }
    }

    //用户名登录
    @Override
    public User checkUserByUsername(String username, String password) {
        return userRepository.findByUsernameAndPassword(username,password);
    }

    //邮箱登录
    @Override
    public User checkUserByEmail(String email, String password) {
        return userRepository.findByEmailAndPassword(email,password);
    }

    //通过ID查询
    @Override
    public User findUserById(Long id) {
        Optional<User> userStatus = userRepository.findById(id);
        return userStatus.orElse(null);
    }

    //通过用户名查询
    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    //邮箱查询
    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    //关键字查询
    @Override
    public Page<User> findUserByQuery(String query, Pageable pageable) {
        return userRepository.findByQuery(query, pageable);
    }

    //更新用户信息
    @Override
    public String updateUser(User user) {
        User userCopy = userRepository.getById(user.getId());
        BeanUtils.copyProperties(user,userCopy, MyBeanUtils.getNullPropertyNames(user));
        userRepository.save(userCopy);
        return "更新成功";
    }

    @Override
    public String upgradeRole(Long id) {
        userRepository.upgradeRole(id);
        return "更新成功";
    }

    @Override
    public String demotionRole(Long id) {
        userRepository.demotionRole(id);
        return "更新成功";
    }

    //禁用用户
    @Transactional
    @Override
    public void deleteUser(Long id) {
        userRepository.deleteUserById(id);
    }

    @Transactional
    @Override
    public void regainUser(Long id) {
        userRepository.regainUser(id);
    }
}