package com.toggn.mma.itp.notice.domain.repository;

import com.toggn.mma.itp.notice.domain.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
}
