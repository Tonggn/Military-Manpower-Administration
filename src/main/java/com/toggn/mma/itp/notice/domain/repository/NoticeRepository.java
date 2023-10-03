package com.toggn.mma.itp.notice.domain.repository;

import com.toggn.mma.itp.notice.domain.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long>, JpaSpecificationExecutor<Notice> {

    @Query("select n from Notice n where n.noticeNumber in :noticeNumbers")
    List<Notice> findAllByNoticeNumberIn(List<Long> noticeNumbers);
}
