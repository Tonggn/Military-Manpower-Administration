package com.toggn.mma.itp.notice.application;

import com.toggn.mma.itp.client.NoticeClient;
import com.toggn.mma.itp.client.OpenAPIClient;
import com.toggn.mma.itp.enterprise.domain.BusinessCode;
import com.toggn.mma.itp.enterprise.domain.Enterprise;
import com.toggn.mma.itp.enterprise.domain.repository.EnterpriseRepository;
import com.toggn.mma.itp.notice.domain.*;
import com.toggn.mma.itp.notice.domain.repository.NoticeRepository;
import com.toggn.mma.itp.notice.parser.NoticeParser;
import com.toggn.mma.itp.notice.parser.dto.NoticeParseResponse;
import org.jsoup.nodes.Document;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class NoticeCommandService {

    private final EnterpriseRepository enterpriseRepository;
    private final NoticeRepository noticeRepository;
    private final OpenAPIClient openAPIClient;

    public NoticeCommandService(
            final EnterpriseRepository enterpriseRepository,
            final NoticeRepository noticeRepository,
            final NoticeClient noticeClient
    ) {
        this.enterpriseRepository = enterpriseRepository;
        this.noticeRepository = noticeRepository;
        this.openAPIClient = noticeClient;
    }

    @Scheduled(cron = "0 10 10-18/2 * * *") // 매일 오전 10시 10분부터 오후 6시 10분까지 2시간마다 실행
    public void updateAllNotices() {
        final Document document = openAPIClient.request();
        final List<NoticeParseResponse> noticeParseResponses = NoticeParser.parseAllNotices(document);
        final List<Notice> notices = convertToNoticeEntities(noticeParseResponses);

        final List<Long> noticeNumbers = notices.stream()
                .map(Notice::getNoticeNumber)
                .toList();
        final List<Notice> savedNotices = noticeRepository.findAllByNoticeNumberIn(noticeNumbers);

        saveUpdatedNotices(notices, savedNotices);
        saveNewNotices(notices, savedNotices);
    }

    private List<Notice> convertToNoticeEntities(final List<NoticeParseResponse> noticeParseResponses) {
        final List<String> enterpriseNames = noticeParseResponses.stream()
                .map(NoticeParseResponse::enterpriseName)
                .toList();
        final Map<String, Long> enterpriseNameIdMap = enterpriseRepository.findAllByNameIn(enterpriseNames).stream()
                .collect(Collectors.toMap(Enterprise::getName, Enterprise::getId));

        return noticeParseResponses.stream()
                .filter(response -> enterpriseNameIdMap.containsKey(response.enterpriseName()))
                .map(response -> convertToNoticeEntity(response, enterpriseNameIdMap.get(response.enterpriseName())))
                .toList();
    }

    private Notice convertToNoticeEntity(final NoticeParseResponse response, final Long enterpriseId) {
        final String title = response.title();
        final String task = response.task();
        final BusinessCode business = BusinessCode.from(response.businessCode());
        final String welfare = response.welfare();
        final SalaryCode salary = SalaryCode.from(response.salaryCode());
        final String serviceAddress = response.serviceAddress();
        final String highestEducationLevel = response.highestEducationLevel();
        final String experienceDivision = response.experienceDivision();
        final ServiceStatusCode serviceStatus = ServiceStatusCode.from(response.serviceStatusCode());
        final AgentCode agent = AgentCode.from(response.agentCode());
        final Long noticeNumber = response.noticeNumber();
        final NoticeDate noticeDate = new NoticeDate(response.createdDate(), response.updatedDate(),
                response.deadlineDate());

        return new Notice(title, task, business, welfare, salary, serviceAddress, highestEducationLevel,
                experienceDivision, serviceStatus, agent, enterpriseId, noticeNumber, noticeDate);
    }

    private void saveUpdatedNotices(final List<Notice> notices, final List<Notice> savedNotices) {
        final Map<Long, Notice> noticeNumberNoticeMap = savedNotices.stream()
                .collect(Collectors.toMap(Notice::getNoticeNumber, notice -> notice));

        notices.stream()
                .filter(notice -> noticeNumberNoticeMap.containsKey(notice.getNoticeNumber()) && noticeNumberNoticeMap.get(notice.getNoticeNumber()).isUpdatable(notice))
                .forEach(notice -> {
                    final Notice savedNotice = noticeNumberNoticeMap.get(notice.getNoticeNumber());
                    savedNotice.update(notice);
                    noticeRepository.save(savedNotice);
                });
    }

    private void saveNewNotices(final List<Notice> notices, final List<Notice> savedNotices) {
        final List<Long> savedNoticeNumbers = savedNotices.stream()
                .map(Notice::getNoticeNumber)
                .toList();

        notices.stream()
                .filter(notice -> !savedNoticeNumbers.contains(notice.getNoticeNumber()))
                .forEach(noticeRepository::save);
    }
}
