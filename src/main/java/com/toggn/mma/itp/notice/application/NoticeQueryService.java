package com.toggn.mma.itp.notice.application;

import com.toggn.mma.itp.enterprise.domain.Enterprise;
import com.toggn.mma.itp.enterprise.domain.repository.EnterpriseRepository;
import com.toggn.mma.itp.notice.application.dto.NoticeFilterRequest;
import com.toggn.mma.itp.notice.application.dto.NoticeResponse;
import com.toggn.mma.itp.notice.domain.Notice;
import com.toggn.mma.itp.notice.domain.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NoticeQueryService {

    private static final int PAGE_SIZE = 20;

    private final NoticeRepository noticeRepository;
    private final EnterpriseRepository enterpriseRepository;

    public Page<NoticeResponse> findAllNotices(final int pageNum, final NoticeFilterRequest noticeFilterRequest) {
        final Pageable pageable = PageRequest.of(pageNum, PAGE_SIZE, Sort.by(Sort.Direction.DESC, "noticeDate_createdDate", "createdAt"));
        final Page<Notice> notices = noticeRepository.findByFiltering(noticeFilterRequest.keyword(),
                noticeFilterRequest.serviceStatusType(),
                noticeFilterRequest.agentType(),
                noticeFilterRequest.serviceAddressKeyword(),
                noticeFilterRequest.businessTypes(),
                pageable);

        final Map<Long, Enterprise> enterpriseMap = getLongEnterpriseMap(notices);

        final List<NoticeResponse> noticeResponses = notices.stream()
                .map(notice -> NoticeResponse.from(notice, enterpriseMap.get(notice.getEnterpriseId())))
                .toList();

        return new PageImpl<>(noticeResponses, pageable, notices.getTotalElements());
    }

    private Map<Long, Enterprise> getLongEnterpriseMap(final Page<Notice> notices) {
        final List<Long> enterpriseIds = notices.stream()
                .map(Notice::getEnterpriseId)
                .toList();
        return enterpriseRepository.findAllByIdIn(enterpriseIds).stream()
                .collect(Collectors.toMap(Enterprise::getId, enterprise -> enterprise));
    }
}
