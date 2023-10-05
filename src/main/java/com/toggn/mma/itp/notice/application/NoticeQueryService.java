package com.toggn.mma.itp.notice.application;

import com.toggn.mma.itp.enterprise.domain.Enterprise;
import com.toggn.mma.itp.enterprise.domain.repository.EnterpriseRepository;
import com.toggn.mma.itp.notice.application.dto.NoticeFilterRequest;
import com.toggn.mma.itp.notice.application.dto.NoticeResponse;
import com.toggn.mma.itp.notice.domain.Notice;
import com.toggn.mma.itp.notice.domain.repository.NoticeRepository;
import com.toggn.mma.itp.notice.domain.repository.NoticeSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
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
        final Specification<Notice> specification = createFilterSpecification(noticeFilterRequest);
        final Page<Notice> notices = noticeRepository.findAll(specification, pageable);

        final Map<Long, Enterprise> enterpriseMap = getLongEnterpriseMap(notices);

        final List<NoticeResponse> noticeResponses = notices.stream()
                .map(notice -> NoticeResponse.from(notice, enterpriseMap.get(notice.getEnterpriseId())))
                .toList();

        return new PageImpl<>(noticeResponses, pageable, notices.getTotalElements());
    }

    private Specification<Notice> createFilterSpecification(final NoticeFilterRequest noticeFilterRequest) {
        Specification<Notice> specification = Specification.where(null);

        if (noticeFilterRequest.isValidKeyword()) {
            specification = specification.and(NoticeSpecification.keywordContains(noticeFilterRequest.keyword()));
        }
        if (noticeFilterRequest.isValidServiceType()) {
            specification = specification.and(NoticeSpecification.serviceStatusTypeEquals(noticeFilterRequest.serviceStatusType()));
        }
        if (noticeFilterRequest.isValidAgentType()) {
            specification = specification.and(NoticeSpecification.agentTypeEquals(noticeFilterRequest.agentType()));
        }
        if (noticeFilterRequest.isValidBusinessTypes()) {
            specification = specification.and(NoticeSpecification.businessTypesIn(noticeFilterRequest.businessTypes()));
        }

        return specification;
    }

    private Map<Long, Enterprise> getLongEnterpriseMap(final Page<Notice> notices) {
        final List<Long> enterpriseIds = notices.stream()
                .map(Notice::getEnterpriseId)
                .toList();
        return enterpriseRepository.findAllByIdIn(enterpriseIds).stream()
                .collect(Collectors.toMap(Enterprise::getId, enterprise -> enterprise));
    }
}
