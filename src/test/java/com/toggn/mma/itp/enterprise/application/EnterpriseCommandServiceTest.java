package com.toggn.mma.itp.enterprise.application;

import com.toggn.mma.itp.enterprise.domain.BusinessType;
import com.toggn.mma.itp.enterprise.domain.Enterprise;
import com.toggn.mma.itp.enterprise.domain.repository.EnterpriseRepository;
import com.toggn.mma.itp.enterprise.parser.dto.EnterpriseParseResponse;
import com.toggn.mma.support.fixture.EnterpriseFixture;
import com.toggn.mma.support.helper.SpringBootTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.toggn.mma.support.fixture.DocumentFixture.enterprisesDocument;
import static com.toggn.mma.support.fixture.EnterpriseFixture.convertToEnterpriseParseResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class EnterpriseCommandServiceTest extends SpringBootTestHelper {

    @Autowired
    private EnterpriseCommandService enterpriseCommandService;
    @Autowired
    private EnterpriseRepository enterpriseRepository;

    private Enterprise enterprise;

    @BeforeEach
    void setUp() {
        enterprise = EnterpriseFixture.enterprise1;
    }

    @Test
    @DisplayName("updateAllEnterprises(): 새로운 기업의 정보를 저장한다.")
    void 새로운_기업_정보_저장_테스트() {
        // given
        final EnterpriseParseResponse enterpriseResponse = convertToEnterpriseParseResponse(enterprise);

        when(enterpriseClient.request()).thenReturn(enterprisesDocument(enterpriseResponse));

        // when
        enterpriseCommandService.updateAllEnterprises();

        // then
        final Enterprise actual = enterpriseRepository.findById(1L).get();

        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(enterprise);
    }

    @Test
    @DisplayName("updateAllEnterprises(): 이름이 같은 업체가 존재할 경우 저장하지 않는다.")
    void 동일_업체명_저장_테스트() {
        // given
        enterpriseRepository.save(enterprise);

        final EnterpriseParseResponse enterpriseResponse = new EnterpriseParseResponse(
                enterprise.getName(),
                BusinessType.TYPE_11101.getCode(),
                "http://another-website.com",
                "another address"
        );

        when(enterpriseClient.request()).thenReturn(enterprisesDocument(enterpriseResponse));

        // when
        enterpriseCommandService.updateAllEnterprises();

        // then
        final List<Enterprise> enterprises = enterpriseRepository.findAll();
        final Enterprise actual = enterprises.get(0);

        assertThat(enterprises).hasSize(1);
        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(enterprise);
    }
}
