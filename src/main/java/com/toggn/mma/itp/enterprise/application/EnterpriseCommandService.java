package com.toggn.mma.itp.enterprise.application;

import com.toggn.mma.itp.client.EnterpriseClient;
import com.toggn.mma.itp.client.OpenAPIClient;
import com.toggn.mma.itp.enterprise.domain.BusinessCode;
import com.toggn.mma.itp.enterprise.domain.Enterprise;
import com.toggn.mma.itp.enterprise.domain.EnterpriseScaleCode;
import com.toggn.mma.itp.enterprise.domain.repository.EnterpriseRepository;
import com.toggn.mma.itp.enterprise.parser.EnterpriseParser;
import com.toggn.mma.itp.enterprise.parser.dto.EnterpriseParseResponse;
import org.jsoup.nodes.Document;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EnterpriseCommandService {

    private final EnterpriseRepository enterpriseRepository;
    private final OpenAPIClient openAPIClient;

    public EnterpriseCommandService(
            final EnterpriseRepository enterpriseRepository,
            final EnterpriseClient enterpriseClient
    ) {
        this.enterpriseRepository = enterpriseRepository;
        this.openAPIClient = enterpriseClient;
    }

    @Scheduled(cron = "0 0 10-18/2 * * *") // 매일 오전 10시부터 오후 6시까지 2시간마다 실행
    public void updateAllEnterprises() {
        final Document document = openAPIClient.request();
        final List<EnterpriseParseResponse> newEnterpriseParseResponses = filterAlreadyExistsEnterprises(
                EnterpriseParser.parseAllEnterprises(document));

        newEnterpriseParseResponses.stream()
                .map(this::convertToEnterpriseEntity)
                .forEach(enterpriseRepository::save);
    }

    private Enterprise convertToEnterpriseEntity(final EnterpriseParseResponse response) {
        final String name = response.name();
        final BusinessCode businessCode = BusinessCode.from(response.businessCode());
        final EnterpriseScaleCode enterpriseScaleCode = EnterpriseScaleCode.from(response.scaleCode());
        final String websiteUrl = response.websiteUrl();
        final String address = response.address();

        return new Enterprise(name, businessCode, enterpriseScaleCode, websiteUrl, address);
    }

    private List<EnterpriseParseResponse> filterAlreadyExistsEnterprises(
            final List<EnterpriseParseResponse> enterpriseParseResponses
    ) {
        final List<Enterprise> enterprises = enterpriseRepository.findAll();
        final List<String> enterpriseNames = enterprises.stream()
                .map(Enterprise::getName)
                .toList();

        return enterpriseParseResponses.stream()
                .filter(response -> !enterpriseNames.contains(response.name()))
                .toList();
    }
}
