package com.toggn.mma.itp.notice.application.dto;

import com.toggn.mma.itp.enterprise.domain.BusinessType;
import com.toggn.mma.itp.notice.domain.AgentType;
import com.toggn.mma.itp.notice.domain.ServiceStatusType;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public record NoticeFilterRequest(
        @RequestParam(value = "keyword", defaultValue = "") String keyword,
        @RequestParam(value = "serviceStatusType", defaultValue = "") ServiceStatusType serviceStatusType,
        @RequestParam(value = "agentType", defaultValue = "") AgentType agentType,
        @RequestParam(value = "businessTypes", defaultValue = "") List<BusinessType> businessTypes
) {

    public boolean isValidKeyword() {
        return keyword != null && !keyword.isBlank();
    }


    public boolean isValidServiceType() {
        return serviceStatusType != null && serviceStatusType.isValid();
    }

    public boolean isValidAgentType() {
        return agentType != null && agentType.isValid();
    }

    public boolean isValidBusinessTypes() {
        return businessTypes != null && !businessTypes.isEmpty() && businessTypes.stream().allMatch(BusinessType::isValid);
    }
}
