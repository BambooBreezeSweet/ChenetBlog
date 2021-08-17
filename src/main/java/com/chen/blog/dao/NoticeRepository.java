/**
 * FileName: NoticeRepository
 * Author:   嘉平十七
 * Date:     2021/8/17 21:22
 * Description:
 */
package com.chen.blog.dao;


import com.chen.blog.domain.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice,Long> {
}
