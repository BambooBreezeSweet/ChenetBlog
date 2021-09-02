package com.chen.website.service;

import com.chen.website.domain.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 公告服务接口
 * @author ChenetChen
 * @since 2021/8/17 17:57
 */
public interface NoticeService {

    //列出所有公告并分页
    Page<Notice> listNotices(Pageable pageable);

    Notice getNoticeById(Long id);

    Notice getAndConvert(Long id);

    //新增公告
    Notice addNotice(Notice notice);

    //修改公告
    Notice editNotice(Long id, Notice notice);

    //删除公告
    void deleteNotice(Long id);
}
