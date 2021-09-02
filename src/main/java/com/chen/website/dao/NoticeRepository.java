package com.chen.website.dao;

import com.chen.website.domain.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author ChenetChen
 * @since 2021/8/17 21:22
 */
public interface NoticeRepository extends JpaRepository<Notice,Long> {
}
