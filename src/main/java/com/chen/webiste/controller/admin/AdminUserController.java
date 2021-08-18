/**
 * FileName: AdminUserController
 * Author:   嘉平十七
 * Date:     2021/3/11 13:19
 * Description: 后台用户管理
 */
package com.chen.webiste.controller.admin;

import com.chen.webiste.domain.User;
import com.chen.webiste.service.TypeService;
import com.chen.webiste.service.UserService;
import com.chen.webiste.utils.MD5Utils;
import com.chen.webiste.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminUserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 设置分类
     * @param model
     */
    private void setType(Model model){
        model.addAttribute("types",typeService.listType());
    }

    /**
     * 后台过滤到登录页面
     * @return
     */
    @GetMapping("")
    public String loginPage(){
        return "admin/login";
    }

    /**
     * 后台登录，需验证管理员标签
     * @param username 用户名或邮箱
     * @param password 密码
     * @param session
     * @param attributes
     * @return
     */
    @PostMapping("/login")
    public String login(@RequestParam String username,
                              @RequestParam String password,
                              HttpSession session,
                              RedirectAttributes attributes){
        User user1 = userService.checkUserByUsername(username, MD5Utils.code(password));
        User user2 = userService.checkUserByEmail(username, MD5Utils.code(password));
        if (user1 != null && (user1.getRole()==1 || user1.getRole() == 2)){
            user1.setPassword(null);
            session.setAttribute("adminUser",user1);
            return "admin/index";
        }else if (user2 != null && (user2.getRole()==1 || user2.getRole() == 2)){
            user2.setPassword(null);
            session.setAttribute("adminUser",user2);
            return "admin/index";
        }else {
            //此处使用重定向，model拿不到
            attributes.addFlashAttribute("message","用户名或密码错误");
            return "redirect:/admin";
        }
    }

    /**
     * 后台查看所有用户
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping("/users")
    public String users(@PageableDefault(size = 5,sort = {"createTime"},direction = Sort.Direction.ASC) Pageable pageable, Model model){
        setType(model);
        model.addAttribute("userList",userService.listUsers(pageable));
        return "admin/users";
    }

    @PostMapping("/users/search")
    public String search(@PageableDefault(size = 5,sort = {"createTime"},direction = Sort.Direction.ASC) Pageable pageable, Model model){
        model.addAttribute("blogs",userService.listUsers(pageable));
        return "admin/users::userList";//返回片段
    }

    /**
     * 后台删除用户
     * 前端最初拼接的th:href无效，所以需要拼接href，因为th标签的解析在js之前，所以占位符也失效，抛出StringToLong的异常。
     * @param id
     * @param attributes
     * @return
     */
    @GetMapping("/user/delete")
    public String delete(Long id, RedirectAttributes attributes,Model model){
        User userBak = userService.findUserById(id);
        redisUtils.set("user"+id,userBak,120); //使用redis备份120秒
        userService.deleteUser(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/users";
    }

    /**
     * 后台修改用户权限
     * @param id
     * @param n
     * @param attributes
     * @return
     */
    @GetMapping("/user/{id}/{n}/updateRole")
    public String updateRole(@PathVariable("id") Long id,@PathVariable("n") int n,RedirectAttributes attributes){
        if (n==1){
            userService.upgradeRole(id);
        }else {
            userService.demotionRole(id);
        }
        attributes.addFlashAttribute("message","操作成功");
        return "redirect:/admin/users";
    }

    /**
     * 后台退出登录
     * @param session
     * @return
     */
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("adminUser");
        return "redirect:/admin";
    }
}