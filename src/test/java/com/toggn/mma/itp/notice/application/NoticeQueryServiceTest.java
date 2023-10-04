package com.toggn.mma.itp.notice.application;

import com.toggn.mma.itp.enterprise.domain.BusinessType;
import com.toggn.mma.itp.enterprise.domain.Enterprise;
import com.toggn.mma.itp.enterprise.domain.repository.EnterpriseRepository;
import com.toggn.mma.itp.notice.application.dto.NoticeFilterRequest;
import com.toggn.mma.itp.notice.application.dto.NoticeResponse;
import com.toggn.mma.itp.notice.domain.*;
import com.toggn.mma.itp.notice.domain.repository.NoticeRepository;
import com.toggn.mma.support.fixture.EnterpriseFixture;
import com.toggn.mma.support.fixture.NoticeFixture;
import com.toggn.mma.support.helper.SpringBootTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
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

    private NoticeFilterRequest nothingFilterRequest;

    @BeforeEach
    void setUp() {
        nothingFilterRequest = new NoticeFilterRequest(null, null, null);
    }

    @Nested
    @DisplayName("findAllNotices(): 공고를 조회 테스트")
    class 공고_조회_테스트 {

        private Enterprise enterprise;
        private List<Notice> notices;

        @BeforeEach
        void setUp() {
            enterprise = enterpriseRepository.save(EnterpriseFixture.ENTERPRISE_1);

            this.notices = IntStream.range(0, 25).mapToObj(i -> NoticeFixture.getNotice(
                    enterprise,
                    SalaryType.TYPE_08,
                    ServiceStatusType.TYPE_002,
                    AgentType.TYPE_1,
                    LocalDate.of(2024, 1, 1),
                    LocalDate.of(2024, 1, 1),
                    LocalDate.of(2024, 2, 1)
            )).toList();
            noticeRepository.saveAll(notices);
        }

        @Test
        @DisplayName("한 페이지에 20개의 공고만 보여준다.")
        void 페이징_테스트() {
            // given
            noticeRepository.saveAll(notices);

            // when
            final Page<NoticeResponse> actual = noticeQueryService.findAllNotices(0, nothingFilterRequest);

            // then
            assertThat(actual.getContent()).hasSize(20);
        }

        @Test
        @DisplayName("모든 공고를 최근 생성일시 순으로 조회한다.")
        void 모든_공고_조회_테스트() {
            // given
            final List<NoticeResponse> expect = notices.stream()
                    .sorted((o1, o2) -> o2.getCreatedAt().compareTo(o1.getCreatedAt()))
                    .map(notice -> NoticeResponse.from(notice, enterprise))
                    .toList()
                    .subList(0, 20);

            // when
            final List<NoticeResponse> actual = noticeQueryService.findAllNotices(0, nothingFilterRequest).getContent();

            // then
            assertThat(actual)
                    .usingRecursiveComparison()
                    .isEqualTo(expect);
        }
    }

    @Nested
    @DisplayName("findAllNotices(): 키워드 필터링 테스트")
    class 키워드_필터링_테스트 {

        private String keyword;
        private Notice keywordNotice;
        private Notice nonKeywordNotice;
        private Enterprise keywordEnterprise;
        private Enterprise nonKeywordEnterprise;

        @BeforeEach
        void setUp() {
            keyword = "키워드";
            keywordEnterprise = enterpriseRepository.save(EnterpriseFixture.ENTERPRISE_1);
            nonKeywordEnterprise = enterpriseRepository.save(EnterpriseFixture.ENTERPRISE_2);

            keywordNotice = noticeRepository.save(
                    new Notice(
                            "prefix" + keyword + "suffix",
                            "task",
                            BusinessType.TYPE_11000,
                            "welfare",
                            SalaryType.TYPE_08,
                            "serviceAddress",
                            "highestEducation",
                            "experienceDivision",
                            ServiceStatusType.TYPE_002,
                            AgentType.TYPE_1,
                            keywordEnterprise.getId(),
                            123L,
                            new NoticeDate(
                                    LocalDate.of(2024, 1, 1),
                                    LocalDate.of(2024, 1, 1),
                                    LocalDate.of(2024, 2, 1)
                            )
                    )
            );
            noticeRepository.save(keywordNotice);

            nonKeywordNotice = noticeRepository.save(
                    NoticeFixture.getNotice(
                            nonKeywordEnterprise,
                            SalaryType.TYPE_08,
                            ServiceStatusType.TYPE_002,
                            AgentType.TYPE_1,
                            LocalDate.of(2024, 1, 1),
                            LocalDate.of(2024, 1, 1),
                            LocalDate.of(2024, 2, 1)
                    )
            );
            noticeRepository.save(nonKeywordNotice);
        }

        @Test
        @DisplayName("키워드를 포함하는 공고의 제목을 검색한다.")
        void 유효_키워드_필터링_테스트() {
            // given
            final String keyword = "키워드";

            final NoticeFilterRequest keywordFilterRequest = new NoticeFilterRequest(keyword, null, null);


            final List<NoticeResponse> expect = List.of(NoticeResponse.from(keywordNotice, keywordEnterprise));

            // when
            final List<NoticeResponse> actual = noticeQueryService.findAllNotices(0, keywordFilterRequest).getContent();

            // then
            assertThat(actual)
                    .usingRecursiveComparison()
                    .isEqualTo(expect);
        }

        @ParameterizedTest
        @NullSource
        @EmptySource
        @DisplayName("키워드가 없으면 모든 공고를 조회한다.")
        void 빈_키워드_필터링_테스트(final String keyword) {
            // given
            final NoticeFilterRequest keywordFilterRequest = new NoticeFilterRequest(keyword, null, null);

            final List<NoticeResponse> expect = List.of(
                    NoticeResponse.from(nonKeywordNotice, nonKeywordEnterprise),
                    NoticeResponse.from(keywordNotice, keywordEnterprise)
            );

            // when
            final List<NoticeResponse> actual = noticeQueryService.findAllNotices(0, keywordFilterRequest).getContent();

            // then
            assertThat(actual)
                    .usingRecursiveComparison()
                    .isEqualTo(expect);
        }
    }
}
