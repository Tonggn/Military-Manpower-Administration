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
        final List<Enterprise> enterprises = enterpriseRepository.findAll();
        final List<NoticeParseResponse> enterpriseExistsNoticeResponses = filterEnterpriseNotExistsNotices(noticeParseResponses, enterprises);
        final List<NoticeParseResponse> newNoticeResponses = filterAlreadyExistsNotices(enterpriseExistsNoticeResponses);

        newNoticeResponses.stream()
                .map(response -> convertToNoticeEntity(response, enterprises))
                .forEach(noticeRepository::save);
    }

    private List<NoticeParseResponse> filterEnterpriseNotExistsNotices(
            final List<NoticeParseResponse> newNoticeParseResponses,
            final List<Enterprise> enterprises
    ) {
        final List<String> enterpriseNames = enterprises.stream()
                .map(Enterprise::getName)
                .toList();

        return newNoticeParseResponses.stream()
                .filter(response -> enterpriseNames.contains(response.enterpriseName()))
                .toList();
    }

    private Notice convertToNoticeEntity(final NoticeParseResponse response, final List<Enterprise> enterprises) {
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
        final Enterprise enterprise = enterprises.stream()
                .filter(ent -> ent.getName().equals(response.enterpriseName()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid enterprise name: " + response.enterpriseName()));
        final Long enterpriseId = enterprise.getId();
        final Long noticeNumber = response.noticeNumber();
        final NoticeDate noticeDate = new NoticeDate(response.createdDate(), response.updatedDate(),
                response.deadlineDate());

        return new Notice(title, task, business, welfare, salary, serviceAddress, highestEducationLevel,
                experienceDivision, serviceStatus, agent, enterpriseId, noticeNumber, noticeDate);
    }

    private List<NoticeParseResponse> filterAlreadyExistsNotices(
            final List<NoticeParseResponse> noticeParseResponses
    ) {
        final List<Notice> notices = noticeRepository.findAll();
        final List<Long> noticeNumbers = notices.stream()
                .map(Notice::getNoticeNumber)
                .toList();

        return noticeParseResponses.stream()
                .filter(response -> !noticeNumbers.contains(response.noticeNumber()))
                .toList();
    }
}
