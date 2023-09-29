package com.toggn.mma.itp.enterprise.application;

import com.toggn.mma.itp.client.EnterpriseClient;
import com.toggn.mma.itp.enterprise.domain.BusinessCode;
import com.toggn.mma.itp.enterprise.domain.Enterprise;
import com.toggn.mma.itp.enterprise.domain.EnterpriseScaleCode;
import com.toggn.mma.itp.enterprise.domain.repository.EnterpriseRepository;
import com.toggn.mma.itp.enterprise.parser.dto.EnterpriseParseResponse;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@SpringBootTest
@Sql(value = "/init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class EnterpriseCommandServiceTest {

    @Autowired
    private EnterpriseCommandService enterpriseCommandService;
    @Autowired
    private EnterpriseRepository enterpriseRepository;
    @MockBean
    private EnterpriseClient enterpriseClient;

    private static Document convertToDocument(final EnterpriseParseResponse... responses) {
        final String xml =
                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><response><body><items>" +
                        Arrays.stream(responses)
                                .map(response -> "<item>" +
                                        "<eopcheNm>" + response.name() + "</eopcheNm>" +
                                        "<eopjongGbcd>" + response.businessCode() + "</eopjongGbcd>" +
                                        "<gegyumoCd>" + response.scaleCode() + "</gegyumoCd>" +
                                        "<hmpgAddr>" + response.websiteUrl() + "</hmpgAddr>" +
                                        "<eopcheAddr>" + response.address() + "</eopcheAddr>" +
                                        "</item>")
                                .collect(Collectors.joining()) +
                        "</items></body></response>";
        return Jsoup.parse(xml, "", Parser.xmlParser());
    }

    @Test
    @DisplayName("updateAllEnterprises(): 새로운 기업의 정보를 저장한다.")
    void 새로운_기업_정보_저장_테스트() {
        // given
        final EnterpriseParseResponse expect = new EnterpriseParseResponse("기업1", "-1", "-1",
                "websiteUrl1", "address1");

        when(enterpriseClient.request()).thenReturn(convertToDocument(expect));

        // when
        enterpriseCommandService.updateAllEnterprises();

        // then
        final Enterprise actual = enterpriseRepository.findById(1L).get();

        assertAll(
                () -> assertThat(actual.getName()).isEqualTo(expect.name()),
                () -> assertThat(actual.getBusiness().getCode()).isEqualTo(expect.businessCode()),
                () -> assertThat(actual.getScale().getCode()).isEqualTo(expect.scaleCode()),
                () -> assertThat(actual.getWebsiteUrl()).isEqualTo(expect.websiteUrl()),
                () -> assertThat(actual.getAddress()).isEqualTo(expect.address())
        );
    }

    @Test
    @DisplayName("updateAllEnterprises(): 이미 존재하는 기업일 경우 저장하지 않는다.")
    void 이미_존재하는_기업_저장_테스트() {
        // given
        final Enterprise savedEnterprise = enterpriseRepository.save(new Enterprise("기업1", BusinessCode.CODE_11111,
                EnterpriseScaleCode.CODE_1, "websiteUrl1", "address1"));

        final EnterpriseParseResponse response = new EnterpriseParseResponse("기업1", "-1", "-1",
                "websiteUrl1", "address1");

        when(enterpriseClient.request()).thenReturn(convertToDocument(response));

        // when
        enterpriseCommandService.updateAllEnterprises();

        // then
        final List<Enterprise> enterprises = enterpriseRepository.findAll();
        final Enterprise actual = enterprises.get(0);

        assertThat(enterprises).hasSize(1);
        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(savedEnterprise);
    }
}
