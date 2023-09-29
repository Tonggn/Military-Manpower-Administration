package com.toggn.mma.itp.notice.application;

import com.toggn.mma.itp.client.NoticeClient;
import com.toggn.mma.itp.enterprise.domain.BusinessCode;
import com.toggn.mma.itp.enterprise.domain.Enterprise;
import com.toggn.mma.itp.enterprise.domain.repository.EnterpriseRepository;
import com.toggn.mma.itp.notice.domain.*;
import com.toggn.mma.itp.notice.domain.repository.NoticeRepository;
import com.toggn.mma.itp.notice.parser.dto.NoticeParseResponse;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@SpringBootTest
@Sql(value = "/init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class NoticeCommandServiceTest {

    @Autowired
    private NoticeCommandService noticeCommandService;
    @Autowired
    private EnterpriseRepository enterpriseRepository;
    @Autowired
    private NoticeRepository noticeRepository;
    @MockBean
    private NoticeClient noticeClient;

    private Enterprise enterprise;
    private NoticeParseResponse newNotice;

    private static Document convertToDocument(final NoticeParseResponse... responses) {
        final String xml =
                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><response><body><items>" +
                        Arrays.stream(responses)
                                .map(response -> "<item>" +
                                        "<cyjemokNm>" + response.title() + "</cyjemokNm>" +
                                        "<ddeopmuNm>" + response.task() + "</ddeopmuNm>" +
                                        "<eopjongGbcd>" + response.businessCode() + "</eopjongGbcd>" +
                                        "<bokrihs>" + response.welfare() + "</bokrihs>" +
                                        "<gyjogeonCd>" + response.salaryCode() + "</gyjogeonCd>" +
                                        "<geunmujy>" + response.serviceAddress() + "</geunmujy>" +
                                        "<cjhakryeok>" + response.highestEducationLevel() + "</cjhakryeok>" +
                                        "<gyeongryeokGbcdNm>" + response.experienceDivision() + "</gyeongryeokGbcdNm>" +
                                        "<yeokjongBrcd>" + response.serviceStatusCode() + "</yeokjongBrcd>" +
                                        "<yowonGbcd>" + response.agentCode() + "</yowonGbcd>" +
                                        "<eopcheNm>" + response.enterpriseName() + "</eopcheNm>" +
                                        "<cygonggoNo>" + response.noticeNumber() + "</cygonggoNo>" +
                                        "<ccdatabalsaengDtm>" + localDateToString(response.createdDate()) + "</ccdatabalsaengDtm>" +
                                        "<cjdatabyeongyeongDtm>" + localDateToString(response.updatedDate()) + "</cjdatabyeongyeongDtm>" +
                                        "<magamDt>" + localDateToString(response.deadlineDate()) + "</magamDt>" +
                                        "</item>")
                                .collect(Collectors.joining()) +
                        "</items></body></response>";
        return Jsoup.parse(xml, "", Parser.xmlParser());
    }

    private static String localDateToString(final LocalDate date) {
        return String.format("%04d%02d%02d", date.getYear(), date.getMonthValue(), date.getDayOfMonth());
    }

    @BeforeEach
    void setUp() {
        enterprise = enterpriseRepository.save(new Enterprise("enterprise name", BusinessCode.CODE_11111, "https://enterprise.website", "enterprise address"));

        newNotice = new NoticeParseResponse(
                "공고 제목",
                "담당 업무",
                BusinessCode.CODE_11111.getCode(),
                "복리후생",
                SalaryCode.CODE07.getCode(),
                "근무 지역",
                "최종학력",
                "경력 구분",
                ServiceStatusCode.CODE002.getCode(),
                AgentCode.CODE1.getCode(),
                enterprise.getName(),
                123L,
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 2, 1)
        );
    }

    @Test
    @DisplayName("updateAllNotices(): 새로운 공고의 정보를 저장한다.")
    void 새로운_공고_저장_테스트() {
        // given
        final NoticeParseResponse expect = newNotice;

        when(noticeClient.request()).thenReturn(convertToDocument(expect));

        // then
        noticeCommandService.updateAllNotices();

        // then
        assertThat(noticeRepository.findAll()).hasSize(1);

        final Notice actual = noticeRepository.findAll().get(0);
        assertAll(
                () -> assertThat(actual.getTitle()).isEqualTo(expect.title()),
                () -> assertThat(actual.getTask()).isEqualTo(expect.task()),
                () -> assertThat(actual.getBusiness().getCode()).isEqualTo(expect.businessCode()),
                () -> assertThat(actual.getWelfare()).isEqualTo(expect.welfare()),
                () -> assertThat(actual.getSalary().getCode()).isEqualTo(expect.salaryCode()),
                () -> assertThat(actual.getServiceAddress()).isEqualTo(expect.serviceAddress()),
                () -> assertThat(actual.getHighestEducationLevel()).isEqualTo(expect.highestEducationLevel()),
                () -> assertThat(actual.getExperienceDivision()).isEqualTo(expect.experienceDivision()),
                () -> assertThat(actual.getServiceStatus().getCode()).isEqualTo(expect.serviceStatusCode()),
                () -> assertThat(actual.getEnterpriseId()).isEqualTo(enterprise.getId()),
                () -> assertThat(actual.getAgent().getCode()).isEqualTo(expect.agentCode()),
                () -> assertThat(actual.getNoticeNumber()).isEqualTo(expect.noticeNumber()),
                () -> assertThat(actual.getNoticeDate().getCreatedDate()).isEqualTo(expect.createdDate()),
                () -> assertThat(actual.getNoticeDate().getUpdatedDate()).isEqualTo(expect.updatedDate()),
                () -> assertThat(actual.getNoticeDate().getDeadlineDate()).isEqualTo(expect.deadlineDate())
        );
    }

    @Test
    @DisplayName("updateAllNotices(): 공고에 해당하는 기업이 존재하지 않을 경우 저장하지 않는다.")
    void 공고에_해당하는_기업이_존재하지_않을_경우_저장하지_않는다() {
        // given
        final NoticeParseResponse enterpriseNotExistsNotice = new NoticeParseResponse(
                "공고 제목",
                "담당 업무",
                BusinessCode.CODE_11111.getCode(),
                "복리후생",
                SalaryCode.CODE07.getCode(),
                "근무 지역",
                "최종학력",
                "경력 구분",
                ServiceStatusCode.CODE002.getCode(),
                AgentCode.CODE1.getCode(),
                "존재하지 않는 기업",
                123L,
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 2, 1)
        );

        when(noticeClient.request()).thenReturn(convertToDocument(enterpriseNotExistsNotice));

        // when
        noticeCommandService.updateAllNotices();

        // then
        assertThat(noticeRepository.findAll()).isEmpty();
    }

    @Test
    @DisplayName("updateAllNotices(): 이미 존재하는 공고일 경우 저장하지 않는다.")
    void 이미_존재하는_공고_저장_테스트() {
        // given
        noticeRepository.save(
                new Notice(
                        newNotice.title(),
                        newNotice.task(),
                        BusinessCode.from(newNotice.businessCode()),
                        newNotice.welfare(),
                        SalaryCode.from(newNotice.salaryCode()),
                        newNotice.serviceAddress(),
                        newNotice.highestEducationLevel(),
                        newNotice.experienceDivision(),
                        ServiceStatusCode.from(newNotice.serviceStatusCode()),
                        AgentCode.from(newNotice.agentCode()),
                        enterprise.getId(),
                        newNotice.noticeNumber(),
                        new NoticeDate(newNotice.createdDate(), newNotice.updatedDate(), newNotice.deadlineDate())
                )
        );

        when(noticeClient.request()).thenReturn(convertToDocument(newNotice));

        // when
        noticeCommandService.updateAllNotices();

        // then
        assertThat(noticeRepository.findAll()).hasSize(1);

        final Notice actual = noticeRepository.findAll().get(0);
        assertAll(
                () -> assertThat(actual.getTitle()).isEqualTo(newNotice.title()),
                () -> assertThat(actual.getTask()).isEqualTo(newNotice.task()),
                () -> assertThat(actual.getBusiness().getCode()).isEqualTo(newNotice.businessCode()),
                () -> assertThat(actual.getWelfare()).isEqualTo(newNotice.welfare()),
                () -> assertThat(actual.getSalary().getCode()).isEqualTo(newNotice.salaryCode()),
                () -> assertThat(actual.getServiceAddress()).isEqualTo(newNotice.serviceAddress()),
                () -> assertThat(actual.getHighestEducationLevel()).isEqualTo(newNotice.highestEducationLevel()),
                () -> assertThat(actual.getExperienceDivision()).isEqualTo(newNotice.experienceDivision()),
                () -> assertThat(actual.getServiceStatus().getCode()).isEqualTo(newNotice.serviceStatusCode()),
                () -> assertThat(actual.getEnterpriseId()).isEqualTo(enterprise.getId()),
                () -> assertThat(actual.getAgent().getCode()).isEqualTo(newNotice.agentCode()),
                () -> assertThat(actual.getNoticeNumber()).isEqualTo(newNotice.noticeNumber()),
                () -> assertThat(actual.getNoticeDate().getCreatedDate()).isEqualTo(newNotice.createdDate()),
                () -> assertThat(actual.getNoticeDate().getUpdatedDate()).isEqualTo(newNotice.updatedDate()),
                () -> assertThat(actual.getNoticeDate().getDeadlineDate()).isEqualTo(newNotice.deadlineDate())
        );
    }
}
