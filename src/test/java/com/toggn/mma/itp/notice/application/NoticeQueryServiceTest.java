package com.toggn.mma.itp.notice.application;

import com.toggn.mma.itp.enterprise.domain.BusinessType;
import com.toggn.mma.itp.enterprise.domain.Enterprise;
import com.toggn.mma.itp.enterprise.domain.repository.EnterpriseRepository;
import com.toggn.mma.itp.notice.application.dto.NoticeFilterRequest;
import com.toggn.mma.itp.notice.application.dto.NoticeResponse;
import com.toggn.mma.itp.notice.domain.*;
import com.toggn.mma.itp.notice.domain.repository.NoticeRepository;
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

    @BeforeEach
    void setUp() {
        enterprise1 = enterpriseRepository.save(new Enterprise("업체1", BusinessType.TYPE_11111, "http://업체1.url", "업체 1 주소지"));
        enterprise2 = enterpriseRepository.save(new Enterprise("업체2", BusinessType.TYPE_11101, "http://업체2.url", "업체 2 주소지"));

        final NoticeDate noticeDate = new NoticeDate(LocalDate.of(2021, 9, 23), LocalDate.of(2021, 9, 23), LocalDate.of(2021, 9, 30));
        notice1 = noticeRepository.save(new Notice("공고 제목1", "업무 내용1", BusinessType.TYPE_11111, "복리후생 내용", SalaryType.TYPE_16, "근무지 주소", "최종학력", "경력 구분", ServiceStatusType.TYPE_002, AgentType.TYPE_1, enterprise1.getId(), 20230923L, noticeDate));
        notice2 = noticeRepository.save(new Notice("공고 제목2", "업무 내용2", BusinessType.TYPE_11101, "복리후생 내용",
                SalaryType.TYPE_16, "근무지 주소", "최종학력", "경력 구분", ServiceStatusType.TYPE_002,
                AgentType.TYPE_1, enterprise2.getId(), 20230923L, noticeDate));
    }

    @Test
    @DisplayName("findAllNotices(): 모든 공고를 최근 생성일시 순으로 조회한다.")
    void 모든_공고_조회_테스트() {
        // given
        final List<NoticeResponse> expect = List.of(
                NoticeResponse.from(notice2, enterprise2),
                NoticeResponse.from(notice1, enterprise1)
        );

        final NoticeFilterRequest noticeFilterRequest = new NoticeFilterRequest(null, null, null);

        // when
        final List<NoticeResponse> actual = noticeQueryService.findAllNotices(0, noticeFilterRequest).getContent();

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

        final NoticeFilterRequest noticeFilterRequest = new NoticeFilterRequest(null, null, null);

        // when
        final Page<NoticeResponse> actual = noticeQueryService.findAllNotices(0, noticeFilterRequest);

        // then
        assertThat(actual.getContent()).hasSize(20);
    }
}
