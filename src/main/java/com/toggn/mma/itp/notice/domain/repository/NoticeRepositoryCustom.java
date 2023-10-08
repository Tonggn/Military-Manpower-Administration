package com.toggn.mma.itp.notice.domain.repository;

import com.toggn.mma.itp.enterprise.domain.BusinessType;
import com.toggn.mma.itp.notice.domain.AgentType;
import com.toggn.mma.itp.notice.domain.Notice;
import com.toggn.mma.itp.notice.domain.ServiceStatusType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NoticeRepositoryCustom {

    Page<Notice> findByFiltering(
            String title,
            ServiceStatusType serviceStatusType,
            AgentType agentType,
            String serviceAddress,
            List<BusinessType> businessTypes,
            Pageable pageable
    );
}
