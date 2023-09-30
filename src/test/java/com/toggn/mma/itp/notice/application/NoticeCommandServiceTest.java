package com.toggn.mma.itp.notice.application;

import com.toggn.mma.itp.enterprise.domain.BusinessCode;
import com.toggn.mma.itp.enterprise.domain.Enterprise;
import com.toggn.mma.itp.enterprise.domain.repository.EnterpriseRepository;
import com.toggn.mma.itp.notice.domain.AgentCode;
import com.toggn.mma.itp.notice.domain.Notice;
import com.toggn.mma.itp.notice.domain.SalaryCode;
import com.toggn.mma.itp.notice.domain.ServiceStatusCode;
import com.toggn.mma.itp.notice.domain.repository.NoticeRepository;
import com.toggn.mma.itp.notice.parser.dto.NoticeParseResponse;
import com.toggn.mma.support.fixture.EnterpriseFixture;
import com.toggn.mma.support.fixture.NoticeFixture;
import com.toggn.mma.support.helper.SpringBootTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

import static com.toggn.mma.support.fixture.DocumentFixture.noticesDocument;
import static com.toggn.mma.support.fixture.NoticeFixture.getNotice;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class NoticeCommandServiceTest extends SpringBootTestHelper {

    @Autowired
    private NoticeCommandService noticeCommandService;
    @Autowired
    private EnterpriseRepository enterpriseRepository;
    @Autowired
    private NoticeRepository noticeRepository;

    private Enterprise enterprise;
    private Notice newNotice;

    @BeforeEach
    void setUp() {
        enterprise = enterpriseRepository.save(EnterpriseFixture.enterprise1);

        newNotice = getNotice(
                enterprise,
                SalaryCode.CODE07,
                ServiceStatusCode.CODE002,
                AgentCode.CODE1,
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 2, 1)
        );
    }

    @Test
    @DisplayName("updateAllNotices(): 새로운 공고의 정보를 저장한다.")
    void 새로운_공고_저장_테스트() {
        // given
        final Notice expect = newNotice;

        final NoticeParseResponse noticeResponse = NoticeFixture.convertToNoticeParseResponse(expect, enterprise);
        when(noticeClient.request()).thenReturn(noticesDocument(noticeResponse));

        // then
        noticeCommandService.updateAllNotices();

        // then
        assertThat(noticeRepository.findAll()).hasSize(1);

        final Notice actual = noticeRepository.findAll().get(0);
        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringFields("id", "createdAt", "updatedAt")
                .isEqualTo(expect);
    }

    @Test
    @DisplayName("updateAllNotices(): 공고에 해당하는 기업이 존재하지 않을 경우 저장하지 않는다.")
    void 공고에_해당하는_기업이_존재하지_않을_경우_저장하지_않는다() {
        // given
        final Enterprise 존재하지_않는_업체 = new Enterprise(
                "존재하지 않는 업체",
                BusinessCode.CODE_11101,
                "http://website.url",
                "존재하지 않는 주소"
        );

        final Notice notice = getNotice(
                존재하지_않는_업체,
                SalaryCode.CODE07,
                ServiceStatusCode.CODE002,
                AgentCode.CODE1,
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 2, 1)
        );

        final NoticeParseResponse noticeResponse = NoticeFixture.convertToNoticeParseResponse(notice, 존재하지_않는_업체);

        when(noticeClient.request()).thenReturn(noticesDocument(noticeResponse));

        // when
        noticeCommandService.updateAllNotices();

        // then
        assertThat(noticeRepository.findAll()).isEmpty();
    }

    @Test
    @DisplayName("updateAllNotices(): 이미 존재하는 공고일 경우 저장하지 않는다.")
    void 이미_존재하는_공고_저장_테스트() {
        // given
        final Notice expect = newNotice;

        noticeRepository.save(expect);

        final NoticeParseResponse noticeResponse = NoticeFixture.convertToNoticeParseResponse(expect, enterprise);
        when(noticeClient.request()).thenReturn(noticesDocument(noticeResponse));

        // when
        noticeCommandService.updateAllNotices();

        // then
        assertThat(noticeRepository.findAll()).hasSize(1);

        final Notice actual = noticeRepository.findAll().get(0);
        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringFields("id", "createdAt", "updatedAt")
                .isEqualTo(expect);
    }
}
