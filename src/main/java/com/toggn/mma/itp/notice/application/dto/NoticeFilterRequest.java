package com.toggn.mma.itp.notice.application.dto;

import com.toggn.mma.itp.notice.domain.AgentType;
import com.toggn.mma.itp.notice.domain.ServiceStatusType;
import org.springframework.web.bind.annotation.RequestParam;

public record NoticeFilterRequest(
        @RequestParam(value = "keyword", defaultValue = "") String keyword,
        @RequestParam(value = "serviceStatusType", defaultValue = "") ServiceStatusType serviceStatusType,
        @RequestParam(value = "agentType", defaultValue = "") AgentType agentType
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
}
