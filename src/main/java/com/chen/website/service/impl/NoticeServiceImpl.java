/**
 * FileName: NoticeServiceImpl
 * Author:   嘉平十七
 * Date:     2021/8/17 21:21
 * Description:
 */
package com.chen.website.service.impl;

import com.chen.website.dao.NoticeRepository;
import com.chen.website.domain.Notice;
import com.chen.website.service.NoticeService;
import com.chen.website.utils.MarkdownUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class NoticeServiceImpl implements NoticeService {
    @Autowired
    NoticeRepository noticeRepository;

    @Transactional
    @Override
    public Page<Notice> listNotices(Pageable pageable){
        return noticeRepository.findAll(pageable);
    }

    @Override
    public Notice getNoticeById(Long id) {
        return noticeRepository.getById(id);
    }

    @Transactional
    @Override
    public Notice getAndConvert(Long id) {
        Notice notice = noticeRepository.getById(id);
        Notice newNotice = new Notice();
        BeanUtils.copyProperties(notice,newNotice);
        String content = newNotice.getContent();
        newNotice.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        return newNotice;
    }

    @Override
    public Notice addNotice(Notice notice) {
        return noticeRepository.save(notice);
    }

    @Override
    public Notice editNotice(Long id, Notice notice) {
        Notice n = noticeRepository.getById(id);
        BeanUtils.copyProperties(notice,n);
        return noticeRepository.save(n);
    }

    @Override
    public void deleteNotice(Long id) {
        noticeRepository.deleteById(id);
    }
}