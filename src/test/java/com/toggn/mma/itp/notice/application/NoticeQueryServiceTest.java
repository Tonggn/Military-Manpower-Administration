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
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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
        nothingFilterRequest = new NoticeFilterRequest(null, null, null, null, null);
    }

    @Nested
    @DisplayName("findAllNotices(): 공고를 조회 테스트")
    class 공고_조회_테스트 {

        private Enterprise enterprise;

        @BeforeEach
        void setUp() {
            enterprise = enterpriseRepository.save(EnterpriseFixture.ENTERPRISE_1);
        }

        @Test
        @DisplayName("한 페이지에 20개의 공고만 보여준다.")
        void 페이징_테스트() {
            // given
            final List<Notice> notices = IntStream.range(0, 25).mapToObj(i -> NoticeFixture.getNotice(
                            enterprise,
                            SalaryType.TYPE_08,
                            ServiceStatusType.TYPE_002,
                            AgentType.TYPE_1,
                            LocalDate.of(2024, 1, 1),
                            LocalDate.of(2024, 1, 1),
                            LocalDate.of(2024, 2, 1)
                    ))
                    .toList();
            noticeRepository.saveAll(notices);

            // when
            final Page<NoticeResponse> actual = noticeQueryService.findAllNotices(0, nothingFilterRequest);

            // then
            assertThat(actual.getContent()).hasSize(20);
        }

        @Test
        @DisplayName("모든 공고를 최근 생성일 순으로 조회한다.")
        void 모든_공고_조회_테스트() {
            // given
            final List<Notice> notices = IntStream.range(1, 10).mapToObj(i -> NoticeFixture.getNotice(
                            enterprise,
                            SalaryType.TYPE_08,
                            ServiceStatusType.TYPE_002,
                            AgentType.TYPE_1,
                            LocalDate.of(2024, 1, i),
                            LocalDate.of(2024, 1, i),
                            LocalDate.of(2024, 2, 1)
                    ))
                    .toList();
            noticeRepository.saveAll(notices);

            final List<NoticeResponse> expect = notices.stream()
                    .sorted((o1, o2) -> o2.getCreatedAt().compareTo(o1.getCreatedAt()))
                    .map(notice -> NoticeResponse.from(notice, enterprise))
                    .toList();

            // when
            final List<NoticeResponse> actual = noticeQueryService.findAllNotices(0, nothingFilterRequest).getContent();

            // then
            assertThat(actual)
                    .usingRecursiveComparison()
                    .isEqualTo(expect);
        }

        @Test
        @DisplayName("공고의 생성일이 같을 경우 최근 DB 추가 일시 순으로 조회한다.")
        void 동일한_생성일_공고_조회_테스트() {
            // given
            final List<Notice> notices = IntStream.range(1, 10).mapToObj(i -> NoticeFixture.getNotice(
                            enterprise,
                            SalaryType.TYPE_08,
                            ServiceStatusType.TYPE_002,
                            AgentType.TYPE_1,
                            LocalDate.of(2024, 1, 1),
                            LocalDate.of(2024, 1, 1),
                            LocalDate.of(2024, 2, 1)
                    ))
                    .toList();
            noticeRepository.saveAll(notices);

            final List<NoticeResponse> expect = notices.stream()
                    .sorted((o1, o2) -> o2.getCreatedAt().compareTo(o1.getCreatedAt()))
                    .map(notice -> NoticeResponse.from(notice, enterprise))
                    .toList();

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

            final NoticeFilterRequest keywordFilterRequest = new NoticeFilterRequest(keyword, null, null, null, null);


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
            final NoticeFilterRequest keywordFilterRequest = new NoticeFilterRequest(keyword, null, null, null, null);

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

    @Nested
    @DisplayName("findAllNotices(): 역종 필터링 테스트")
    class 역종_필터링_테스트 {

        private Enterprise enterprise;
        private List<Notice> notices;

        static Stream<ServiceStatusType> 유효_역종_필터링_테스트() {
            return ServiceStatusType.validValues().stream();
        }

        static Stream<ServiceStatusType> 유효하지_않은_역종_필터링_테스트() {
            return Stream.of(ServiceStatusType.TYPE_UNLISTED, null);
        }

        @BeforeEach
        void setUp() {
            enterprise = enterpriseRepository.save(EnterpriseFixture.ENTERPRISE_1);

            notices = Arrays.stream(ServiceStatusType.values())
                    .map(serviceStatusType -> NoticeFixture.getNotice(
                                    enterprise,
                                    SalaryType.TYPE_08,
                                    serviceStatusType,
                                    AgentType.TYPE_1,
                                    LocalDate.of(2024, 1, 1),
                                    LocalDate.of(2024, 1, 1),
                                    LocalDate.of(2024, 2, 1)
                            )
                    )
                    .toList();
            noticeRepository.saveAll(notices);
        }

        @ParameterizedTest
        @MethodSource
        @DisplayName("역종에 해당하는 공고만 조회한다.")
        void 유효_역종_필터링_테스트(final ServiceStatusType serviceStatusType) {
            // given
            final NoticeFilterRequest serviceStatusFilterRequest = new NoticeFilterRequest(null, serviceStatusType, null, null, null);

            final List<NoticeResponse> expect = notices.stream()
                    .filter(notice -> notice.getServiceStatusType().equals(serviceStatusType))
                    .map(notice -> NoticeResponse.from(notice, enterprise))
                    .toList();

            // when
            final List<NoticeResponse> actual = noticeQueryService.findAllNotices(0, serviceStatusFilterRequest).getContent();

            // then
            assertThat(actual)
                    .usingRecursiveComparison()
                    .isEqualTo(expect);
        }

        @ParameterizedTest
        @MethodSource
        @DisplayName("유효하지 않은 역종일 경우 모든 역종의 공고를 조회한다.")
        void 유효하지_않은_역종_필터링_테스트(final ServiceStatusType serviceStatusType) {
            // given
            final NoticeFilterRequest serviceStatusFilterRequest = new NoticeFilterRequest(null, serviceStatusType, null, null, null);

            final List<NoticeResponse> expect = notices.stream()
                    .sorted((o1, o2) -> o2.getCreatedAt().compareTo(o1.getCreatedAt()))
                    .map(notice -> NoticeResponse.from(notice, enterprise))
                    .toList();


            // when
            final List<NoticeResponse> actual = noticeQueryService.findAllNotices(0, serviceStatusFilterRequest).getContent();

            // then
            assertThat(actual)
                    .usingRecursiveComparison()
                    .isEqualTo(expect);
        }
    }

    @Nested
    @DisplayName("findAllNotices(): 요원 필터링 테스트")
    class 요원_필터링_테스트 {

        private Enterprise enterprise;
        private List<Notice> notices;

        static Stream<AgentType> 유효_요원_필터링_테스트() {
            return AgentType.validValues().stream();
        }

        static Stream<AgentType> 유효하지_않은_요원_필터링_테스트() {
            return Stream.of(AgentType.TYPE_UNLISTED, null);
        }

        @BeforeEach
        void setUp() {
            enterprise = enterpriseRepository.save(EnterpriseFixture.ENTERPRISE_1);

            notices = Arrays.stream(AgentType.values())
                    .map(agentType -> NoticeFixture.getNotice(
                                    enterprise,
                                    SalaryType.TYPE_08,
                                    ServiceStatusType.TYPE_002,
                                    agentType,
                                    LocalDate.of(2024, 1, 1),
                                    LocalDate.of(2024, 1, 1),
                                    LocalDate.of(2024, 2, 1)
                            )
                    )
                    .toList();
            noticeRepository.saveAll(notices);
        }

        @ParameterizedTest
        @MethodSource
        @DisplayName("요원 분류에 해당하는 공고만 조회한다.")
        void 유효_요원_필터링_테스트(final AgentType agentType) {
            // given
            final NoticeFilterRequest serviceStatusFilterRequest = new NoticeFilterRequest(null, null, agentType, null, null);

            final List<NoticeResponse> expect = notices.stream()
                    .filter(notice -> notice.getAgentType().equals(agentType))
                    .map(notice -> NoticeResponse.from(notice, enterprise))
                    .toList();

            // when
            final List<NoticeResponse> actual = noticeQueryService.findAllNotices(0, serviceStatusFilterRequest).getContent();

            // then
            assertThat(actual)
                    .usingRecursiveComparison()
                    .isEqualTo(expect);
        }

        @ParameterizedTest
        @MethodSource
        @DisplayName("유효하지 않은 요원 분류일 경우 모든 역종의 공고를 조회한다.")
        void 유효하지_않은_요원_필터링_테스트(final AgentType agentType) {
            // given
            final NoticeFilterRequest serviceStatusFilterRequest = new NoticeFilterRequest(null, null, agentType, null, null);

            final List<NoticeResponse> expect = notices.stream()
                    .sorted((o1, o2) -> o2.getCreatedAt().compareTo(o1.getCreatedAt()))
                    .map(notice -> NoticeResponse.from(notice, enterprise))
                    .toList();


            // when
            final List<NoticeResponse> actual = noticeQueryService.findAllNotices(0, serviceStatusFilterRequest).getContent();

            // then
            assertThat(actual)
                    .usingRecursiveComparison()
                    .isEqualTo(expect);
        }
    }

    @Nested
    @DisplayName("findAllNotices(): 업종 필터링 테스트")
    class 업종_필터링_테스트 {

        private Enterprise enterprise;
        private List<Notice> notices;

        static Stream<List<BusinessType>> 유효_업종_필터링_테스트() {
            final List<BusinessType> items = BusinessType.validValues().stream().toList();


            return Stream.of(
                    items.subList(2, 5),
                    items.subList(6, 22),
                    items.subList(66, 70),
                    items.subList(76, 77),
                    items.subList(23, 35)
            );
        }

        static Stream<List<BusinessType>> 유효하지_않은_업종_필터링_테스트() {
            return Stream.of(
                    Collections.emptyList(),
                    List.of(BusinessType.TYPE_UNLISTED),
                    null
            );
        }

        @BeforeEach
        void setUp() {
            enterprise = enterpriseRepository.save(EnterpriseFixture.ENTERPRISE_1);

            notices = Arrays.stream(BusinessType.values())
                    .map(businessType -> NoticeFixture.getNotice(
                                    enterprise,
                                    SalaryType.TYPE_08,
                                    ServiceStatusType.TYPE_002,
                                    AgentType.TYPE_1,
                                    LocalDate.of(2024, 1, 1),
                                    LocalDate.of(2024, 1, 1),
                                    LocalDate.of(2024, 2, 1)
                            )
                    )
                    .toList();
            noticeRepository.saveAll(notices);
        }

        @ParameterizedTest
        @MethodSource
        @DisplayName("선택된 업종에 해당하는 공고만 조회한다.")
        void 유효_업종_필터링_테스트(final List<BusinessType> businessTypes) {
            // given
            final NoticeFilterRequest serviceStatusFilterRequest = new NoticeFilterRequest(null, null, null, null, businessTypes);

            final List<NoticeResponse> expect = notices.stream()
                    .filter(notice -> businessTypes.contains(notice.getBusinessType()))
                    .sorted((o1, o2) -> o2.getCreatedAt().compareTo(o1.getCreatedAt()))
                    .map(notice -> NoticeResponse.from(notice, enterprise))
                    .toList();

            // when
            final List<NoticeResponse> actual = noticeQueryService.findAllNotices(0, serviceStatusFilterRequest).getContent();

            // then
            assertThat(actual)
                    .usingRecursiveComparison()
                    .isEqualTo(expect);
        }

        @ParameterizedTest
        @MethodSource
        @DisplayName("유효하지 않은 업종일 경우 모든 역종의 공고를 조회한다.")
        void 유효하지_않은_업종_필터링_테스트(final List<BusinessType> businessTypes) {
            // given
            final NoticeFilterRequest serviceStatusFilterRequest = new NoticeFilterRequest(null, null, null, null, businessTypes);

            final List<NoticeResponse> expect = notices.stream()
                    .sorted((o1, o2) -> o2.getCreatedAt().compareTo(o1.getCreatedAt()))
                    .map(notice -> NoticeResponse.from(notice, enterprise))
                    .toList()
                    .subList(0, 20);


            // when
            final List<NoticeResponse> actual = noticeQueryService.findAllNotices(0, serviceStatusFilterRequest).getContent();

            // then
            assertThat(actual)
                    .usingRecursiveComparison()
                    .isEqualTo(expect);
        }
    }

    @Nested
    @DisplayName("findAllNotices(): 근무지 검색 테스트")
    class 근무지_검색_테스트 {

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
                            "title",
                            "task",
                            BusinessType.TYPE_11000,
                            "welfare",
                            SalaryType.TYPE_08,
                            "prefix" + keyword + "suffix",
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
        @DisplayName("근무지에 키워드가 포함된 공고만 조회한다.")
        void 근무지_유효_키워드_검색_테스트() {
            // given
            final String keyword = "키워드";

            final NoticeFilterRequest serviceAddressFilter = new NoticeFilterRequest(null, null, null, keyword, null);

            final List<NoticeResponse> expect = List.of(NoticeResponse.from(keywordNotice, keywordEnterprise));

            // when
            final List<NoticeResponse> actual = noticeQueryService.findAllNotices(0, serviceAddressFilter).getContent();

            // then
            assertThat(actual)
                    .usingRecursiveComparison()
                    .isEqualTo(expect);
        }

        @ParameterizedTest
        @NullSource
        @EmptySource
        @DisplayName("유효하지 않은 키워드 입력시 모든 공고를 조회한다.")
        void 근무지_유효하지_않은_키워드_검색_테스트(final String keyword) {
            // given
            final NoticeFilterRequest serviceAddressFilter = new NoticeFilterRequest(null, null, null, keyword, null);

            final List<NoticeResponse> expect = List.of(
                    NoticeResponse.from(nonKeywordNotice, nonKeywordEnterprise),
                    NoticeResponse.from(keywordNotice, keywordEnterprise)
            );

            // when
            final List<NoticeResponse> actual = noticeQueryService.findAllNotices(0, serviceAddressFilter).getContent();

            // then
            assertThat(actual)
                    .usingRecursiveComparison()
                    .isEqualTo(expect);
        }
    }
}
