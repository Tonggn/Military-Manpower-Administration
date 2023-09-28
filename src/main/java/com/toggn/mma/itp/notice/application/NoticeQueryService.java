package com.toggn.mma.itp.notice.application;

import com.toggn.mma.itp.enterprise.domain.Enterprise;
import com.toggn.mma.itp.enterprise.domain.repository.EnterpriseRepository;
import com.toggn.mma.itp.notice.application.dto.NoticeResponse;
import com.toggn.mma.itp.notice.domain.Notice;
import com.toggn.mma.itp.notice.domain.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NoticeQueryService {

    private final NoticeRepository noticeRepository;
    private final EnterpriseRepository enterpriseRepository;

    public List<NoticeResponse> findAllNotices() {
        final List<Notice> notices = noticeRepository.findAll();
        final List<Long> enterpriseIds = notices.stream()
                .map(Notice::getEnterpriseId)
                .toList();
        final Map<Long, Enterprise> enterpriseMap = enterpriseRepository.findAllByIdIn(enterpriseIds).stream()
                .collect(Collectors.toMap(Enterprise::getId, enterprise -> enterprise));

        return notices.stream()
                .map(notice -> NoticeResponse.from(notice, enterpriseMap.get(notice.getEnterpriseId())))
                .toList();
    }
}
