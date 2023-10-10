package com.toggn.mma.itp.notice.application;

import com.toggn.mma.itp.notice.application.dto.NoticeFilterRequest;
import com.toggn.mma.itp.notice.application.dto.NoticeResponse;
import com.toggn.mma.itp.notice.domain.Notice;
import com.toggn.mma.itp.notice.domain.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NoticeQueryService {

    private static final int PAGE_SIZE = 20;

    private final NoticeRepository noticeRepository;

    public Page<NoticeResponse> findAllNotices(final int pageNum, final NoticeFilterRequest noticeFilterRequest) {
        final Pageable pageable = PageRequest.of(pageNum, PAGE_SIZE, Sort.by(Sort.Direction.DESC, "noticeDate_createdDate", "createdAt"));
        final Page<Notice> notices = noticeRepository.findByFiltering(noticeFilterRequest.keyword(),
                noticeFilterRequest.serviceStatusType(),
                noticeFilterRequest.agentType(),
                noticeFilterRequest.serviceAddressKeyword(),
                noticeFilterRequest.businessTypes(),
                pageable);

        final List<NoticeResponse> noticeResponses = notices.stream()
                .map(notice -> NoticeResponse.from(notice, notice.getEnterprise()))
                .toList();

        return new PageImpl<>(noticeResponses, pageable, notices.getTotalElements());
    }
}
