package com.toggn.mma.itp.notice.application;

import com.toggn.mma.itp.enterprise.domain.BusinessCode;
import com.toggn.mma.itp.enterprise.domain.Enterprise;
import com.toggn.mma.itp.enterprise.domain.EnterpriseScaleCode;
import com.toggn.mma.itp.enterprise.domain.repository.EnterpriseRepository;
import com.toggn.mma.itp.notice.application.dto.NoticeResponse;
import com.toggn.mma.itp.notice.domain.*;
import com.toggn.mma.itp.notice.domain.repository.NoticeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
@Sql(value = "/init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class NoticeQueryServiceTest {

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
        enterprise1 = enterpriseRepository.save(new Enterprise("업체1", BusinessCode.CODE_11111,
                EnterpriseScaleCode.CODE_2, "http://업체1.url", "업체 1 주소지"));
        enterprise2 = enterpriseRepository.save(new Enterprise("업체2", BusinessCode.CODE_11101,
                EnterpriseScaleCode.CODE_2, "http://업체2.url", "업체 2 주소지"));

        final NoticeDate noticeDate = new NoticeDate(LocalDate.of(2021, 9, 23), LocalDate.of(2021, 9, 23), LocalDate.of(2021, 9, 30));
        notice1 = noticeRepository.save(new Notice("공고 제목1", "업무 내용1", BusinessCode.CODE_11111, "복리후생 내용",
                SalaryCode.CODE16, "근무지 주소", "최종학력", "경력 구분", ServiceStatusCode.CODE002,
                AgentCode.CODE1, enterprise1.getId(), 20230923L, noticeDate));
        notice2 = noticeRepository.save(new Notice("공고 제목2", "업무 내용2", BusinessCode.CODE_11101, "복리후생 내용",
                SalaryCode.CODE16, "근무지 주소", "최종학력", "경력 구분", ServiceStatusCode.CODE002,
                AgentCode.CODE1, enterprise2.getId(), 20230923L, noticeDate));
    }

    @Test
    @DisplayName("findAllNotices(): 모든 공고를 조회한다.")
    void 모든_공고_조회_테스트() {
        // given
        final List<NoticeResponse> expect = List.of(NoticeResponse.from(notice1, enterprise1), NoticeResponse.from(notice2, enterprise2));

        // when
        final List<NoticeResponse> actual = noticeQueryService.findAllNotices();

        // then
        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expect);
    }
}
