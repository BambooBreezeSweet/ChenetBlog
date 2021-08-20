/**
 * FileName: AdminNoticesController
 * Author:   嘉平十七
 * Date:     2021/8/17 23:16
 * Description: 后台公告管理
 */
package com.chen.website.controller.admin;

import com.chen.website.domain.Notice;
import com.chen.website.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Date;

@Controller
@RequestMapping("/admin")
public class AdminNoticesController {
    @Autowired
    private NoticeService noticeService;

    @GetMapping("/notices")
    public String notices(@PageableDefault(size = 5,sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable, Model model){
        model.addAttribute("notices",noticeService.listNotices(pageable));
        return "admin/notices";
    }

    @GetMapping("/notice/input")
    public String input(Model model){
        model.addAttribute("notice",new Notice());
        return "admin/notice-input";
    }

    @PostMapping("/notice/input")
    public String post(@Valid Notice notice, BindingResult result, RedirectAttributes attributes){
        //有错误返回
        if (result.hasErrors()){
            return "admin/notice-input";
        }
        notice.setCreateTime(new Date());
        Notice newNotice = noticeService.addNotice(notice);

        if (newNotice == null){
            attributes.addFlashAttribute("message","新增失败");
        }else {
            attributes.addFlashAttribute("message","新增成功");
        }
        return "redirect:/admin/notices";
    }

    @GetMapping("/notice/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("notice",noticeService.getNoticeById(id));
        return "admin/notice-input";
    }

    /*更新的方法，可以和上面共用*/
    @PostMapping("/notice/{id}")
    public String editPost(@Valid Notice notice, @PathVariable Long id, RedirectAttributes attributes){
        notice.setCreateTime(new Date());
        Notice t = noticeService.editNotice(id,notice);
        if (t == null){
            attributes.addFlashAttribute("message","更新失败");
        }else {
            attributes.addFlashAttribute("message","更新成功");
        }
        return "redirect:/admin/notices";
    }

    @GetMapping("/notice/delete")
    public String delete(Long id,RedirectAttributes attributes){
        noticeService.deleteNotice(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/notices";
    }

}