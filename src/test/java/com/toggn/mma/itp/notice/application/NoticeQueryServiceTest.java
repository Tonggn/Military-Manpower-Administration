package com.toggn.mma.itp.notice.application;

import com.toggn.mma.itp.enterprise.domain.Enterprise;
import com.toggn.mma.itp.enterprise.domain.repository.EnterpriseRepository;
import com.toggn.mma.itp.notice.application.dto.NoticeFilterRequest;
import com.toggn.mma.itp.notice.application.dto.NoticeResponse;
import com.toggn.mma.itp.notice.domain.AgentType;
import com.toggn.mma.itp.notice.domain.Notice;
import com.toggn.mma.itp.notice.domain.SalaryType;
import com.toggn.mma.itp.notice.domain.ServiceStatusType;
import com.toggn.mma.itp.notice.domain.repository.NoticeRepository;
import com.toggn.mma.support.fixture.EnterpriseFixture;
import com.toggn.mma.support.fixture.NoticeFixture;
import com.toggn.mma.support.helper.SpringBootTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;


class NoticeQueryServiceTest extends SpringBootTestHelper {

    @Autowired
    private NoticeQueryService noticeQueryService;
    @Autowired
    private NoticeRepository noticeRepository;
    @Autowired
    private EnterpriseRepository enterpriseRepository;

    private Enterprise enterprise1;
    private Enterprise enterprise2;
    private Notice notice1;
    private Notice notice2;
    private NoticeFilterRequest nothingFilterRequest;

    @BeforeEach
    void setUp() {
        enterprise1 = enterpriseRepository.save(EnterpriseFixture.ENTERPRISE_1);
        enterprise2 = enterpriseRepository.save(EnterpriseFixture.ENTERPRISE_2);

        notice1 = noticeRepository.save(
                NoticeFixture.getNotice(
                        enterprise1,
                        SalaryType.TYPE_16,
                        ServiceStatusType.TYPE_002,
                        AgentType.TYPE_1,
                        LocalDate.of(2024, 1, 1),
                        LocalDate.of(2024, 1, 1),
                        LocalDate.of(2024, 2, 1)
                )
        );
        notice2 = noticeRepository.save(
                NoticeFixture.getNotice(
                        enterprise2,
                        SalaryType.TYPE_08,
                        ServiceStatusType.TYPE_002,
                        AgentType.TYPE_1,
                        LocalDate.of(2024, 1, 1),
                        LocalDate.of(2024, 1, 1),
                        LocalDate.of(2024, 2, 1)
                )
        );

        nothingFilterRequest = new NoticeFilterRequest(null, null, null);
    }

    @Test
    @DisplayName("findAllNotices(): 모든 공고를 최근 생성일시 순으로 조회한다.")
    void 모든_공고_조회_테스트() {
        // given
        final List<NoticeResponse> expect = List.of(
                NoticeResponse.from(notice2, enterprise2),
                NoticeResponse.from(notice1, enterprise1)
        );

        // when
        final List<NoticeResponse> actual = noticeQueryService.findAllNotices(0, nothingFilterRequest).getContent();

        // then
        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expect);
    }

    @Test
    @DisplayName("findAllNotices(): 한 페이지에 20개의 공고만 보여준다.")
    void 페이징_테스트() {
        // given
        final List<Notice> notices = IntStream.range(0, 30).mapToObj(i -> NoticeFixture.getNotice(
                enterprise1,
                SalaryType.TYPE_08,
                ServiceStatusType.TYPE_002,
                AgentType.TYPE_1,
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 2, 1)
        )).toList();

        noticeRepository.saveAll(notices);

        // when
        final Page<NoticeResponse> actual = noticeQueryService.findAllNotices(0, nothingFilterRequest);

        // then
        assertThat(actual.getContent()).hasSize(20);
    }
}
