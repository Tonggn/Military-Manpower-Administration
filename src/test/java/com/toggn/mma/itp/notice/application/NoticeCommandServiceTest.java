package com.toggn.mma.itp.notice.application;

import com.toggn.mma.itp.enterprise.domain.BusinessType;
import com.toggn.mma.itp.enterprise.domain.Enterprise;
import com.toggn.mma.itp.enterprise.domain.repository.EnterpriseRepository;
import com.toggn.mma.itp.notice.domain.*;
import com.toggn.mma.itp.notice.domain.repository.NoticeRepository;
import com.toggn.mma.itp.notice.parser.dto.NoticeParseResponse;
import com.toggn.mma.support.fixture.EnterpriseBuilder;
import com.toggn.mma.support.fixture.NoticeBuilder;
import com.toggn.mma.support.helper.SpringBootTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

import static com.toggn.mma.support.fixture.DocumentFixture.noticesDocument;
import static com.toggn.mma.support.fixture.ParseResponseConverter.toNoticeParseResponse;
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
        enterprise = enterpriseRepository.save(new EnterpriseBuilder().build());

        newNotice = new NoticeBuilder(enterprise).build();
    }

    @Test
    @DisplayName("updateAllNotices(): 새로운 공고의 정보를 저장한다.")
    void 새로운_공고_저장_테스트() {
        // given
        final Notice expect = newNotice;

        final NoticeParseResponse noticeResponse = toNoticeParseResponse(expect, enterprise);
        when(noticeClient.request()).thenReturn(noticesDocument(noticeResponse));

        // then
        noticeCommandService.updateAllNotices();

        // then
        assertThat(noticeRepository.findAll()).hasSize(1);

        final Notice actual = noticeRepository.findAll().get(0);
        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringFields("id", "enterprise", "createdAt", "updatedAt")
                .isEqualTo(expect);
    }

    @Test
    @DisplayName("updateAllNotices(): 공고의 생성일이 빠른 것 부터 저장한다.")
    void 빠른_생성일순_저장_테스트() {
        // given
        final Notice notice_1월_1일 = new NoticeBuilder(enterprise)
                .setCreateDate(LocalDate.of(2024, 1, 1))
                .build();
        final Notice notice_1월_2일 = new NoticeBuilder(enterprise)
                .setCreateDate(LocalDate.of(2024, 1, 2))
                .build();
        final Notice notice_1월_3일 = new NoticeBuilder(enterprise)
                .setCreateDate(LocalDate.of(2024, 1, 3))
                .build();

        final NoticeParseResponse noticeResponse1 = toNoticeParseResponse(notice_1월_1일, enterprise);
        final NoticeParseResponse noticeResponse2 = toNoticeParseResponse(notice_1월_2일, enterprise);
        final NoticeParseResponse noticeResponse3 = toNoticeParseResponse(notice_1월_3일, enterprise);

        when(noticeClient.request()).thenReturn(noticesDocument(noticeResponse2, noticeResponse3, noticeResponse1));

        // then
        noticeCommandService.updateAllNotices();

        // then
        final List<Notice> actual = noticeRepository.findAll().stream()
                .sorted(Comparator.comparing(Notice::getId))
                .toList();

        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringFields("id", "enterprise", "createdAt", "updatedAt")
                .isEqualTo(List.of(notice_1월_1일, notice_1월_2일, notice_1월_3일));
    }

    @Test
    @DisplayName("updateAllNotices(): 공고에 해당하는 기업이 존재하지 않을 경우 저장하지 않는다.")
    void 공고에_해당하는_기업이_존재하지_않을_경우_저장하지_않는다() {
        // given
        final Enterprise 존재하지_않는_업체 = new Enterprise(
                "존재하지 않는 업체",
                BusinessType.TYPE_11101,
                "http://website.url",
                "존재하지 않는 주소"
        );

        final Notice notice = new NoticeBuilder(존재하지_않는_업체).build();

        final NoticeParseResponse noticeResponse = toNoticeParseResponse(notice, 존재하지_않는_업체);

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

        final NoticeParseResponse noticeResponse = toNoticeParseResponse(expect, enterprise);
        when(noticeClient.request()).thenReturn(noticesDocument(noticeResponse));

        // when
        noticeCommandService.updateAllNotices();

        // then
        assertThat(noticeRepository.findAll()).hasSize(1);

        final Notice actual = noticeRepository.findAll().get(0);
        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringFields("id", "enterprise", "createdAt", "updatedAt")
                .isEqualTo(expect);

    }

    @Test
    @DisplayName("updateAllNotices(): 기존 공고의 정보를 업데이트한다.")
    void 기존_공고_업데이트_테스트() {
        // given
        final Notice savedNotice = noticeRepository.save(newNotice);

        final Notice updatedNotice = new Notice(
                "업데이트된 제목",
                "업데이트된 업무",
                savedNotice.getBusinessType(),
                "업데이트된 복리후생",
                SalaryType.TYPE_09,
                savedNotice.getServiceAddress(),
                "업데이트된 최종학력",
                "업데이트된 경력구분",
                ServiceStatusType.TYPE_006,
                AgentType.TYPE_2,
                savedNotice.getEnterprise(),
                savedNotice.getNoticeNumber(),
                new NoticeDate(
                        LocalDate.of(2024, 1, 1),
                        LocalDate.of(2024, 2, 1),
                        LocalDate.of(2024, 3, 1)
                )
        );

        final NoticeParseResponse noticeResponse = toNoticeParseResponse(updatedNotice, enterprise);
        when(noticeClient.request()).thenReturn(noticesDocument(noticeResponse));

        // when
        noticeCommandService.updateAllNotices();

        // then
        assertThat(noticeRepository.findAll()).hasSize(1);

        final Notice actual = noticeRepository.findAll().get(0);
        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringFields("id", "enterprise", "createdAt", "updatedAt")
                .isEqualTo(updatedNotice);
        assertThat(actual.getCreatedAt().isBefore(actual.getUpdatedAt())).isTrue();
    }

    @Test
    @DisplayName("updateAllNotices(): 변경 사항이 없는 공고는 업데이트가 일어나지 않는다.")
    void 동일_공고_업데이트_테스트() {
        // given
        final Notice savedNotice = noticeRepository.save(newNotice);

        final NoticeParseResponse noticeResponse = toNoticeParseResponse(savedNotice, enterprise);
        when(noticeClient.request()).thenReturn(noticesDocument(noticeResponse));

        // then
        noticeCommandService.updateAllNotices();

        // then
        assertThat(noticeRepository.findAll()).hasSize(1);

        final Notice actual = noticeRepository.findAll().get(0);
        assertThat(actual.getCreatedAt().isEqual(actual.getUpdatedAt())).isTrue();
    }
}
