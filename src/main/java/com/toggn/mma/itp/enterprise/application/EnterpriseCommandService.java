package com.toggn.mma.itp.enterprise.application;

import com.toggn.mma.itp.client.EnterpriseClient;
import com.toggn.mma.itp.client.OpenAPIClient;
import com.toggn.mma.itp.enterprise.domain.BusinessType;
import com.toggn.mma.itp.enterprise.domain.Enterprise;
import com.toggn.mma.itp.enterprise.domain.repository.EnterpriseRepository;
import com.toggn.mma.itp.enterprise.parser.EnterpriseParser;
import com.toggn.mma.itp.enterprise.parser.dto.EnterpriseParseResponse;
import org.jsoup.nodes.Document;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Scheduled(cron = "0 0 * * * *")
    public void updateAllEnterprises() {
        final Document document = openAPIClient.request();
        final Set<EnterpriseParseResponse> newEnterpriseResponses = excludeExistsEnterprise(
                EnterpriseParser.parseAll(document));

        newEnterpriseResponses.stream()
                .map(this::convertToEnterpriseEntity)
                .forEach(enterpriseRepository::save);
    }

    private Enterprise convertToEnterpriseEntity(final EnterpriseParseResponse response) {
        final String name = response.name();
        final BusinessType businessType = BusinessType.from(response.businessTypeCode());
        final String websiteUrl = response.websiteUrl();
        final String address = response.address();

        return new Enterprise(name, businessType, websiteUrl, address);
    }

    private Set<EnterpriseParseResponse> excludeExistsEnterprise(
            final List<EnterpriseParseResponse> enterpriseParseResponses
    ) {
        final List<String> enterpriseNames = enterpriseRepository.findAllNames();

        return enterpriseParseResponses.stream()
                .filter(response -> !enterpriseNames.contains(response.name()))
                .collect(Collectors.toSet());
    }
}
