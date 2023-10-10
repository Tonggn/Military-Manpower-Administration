package com.toggn.mma.itp.notice.application.dto;

import com.toggn.mma.itp.enterprise.domain.BusinessType;
import com.toggn.mma.itp.notice.domain.AgentType;
import com.toggn.mma.itp.notice.domain.ServiceStatusType;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.List;

public record NoticeFilterRequest(
        @RequestParam(value = "keyword", defaultValue = "") String keyword,
        @RequestParam(value = "serviceStatusType", defaultValue = "") ServiceStatusType serviceStatusType,
        @RequestParam(value = "agentType", defaultValue = "") AgentType agentType,
        @RequestParam(value = "serviceAddressKeyword", defaultValue = "") String serviceAddressKeyword,
        @RequestParam(value = "enterpriseNameKeyword", defaultValue = "") String enterpriseNameKeyword,
        @RequestParam(value = "businessTypes", defaultValue = "") List<BusinessType> businessTypes
) {

    @Override
    public List<BusinessType> businessTypes() {
        return businessTypes == null || businessTypes.equals(List.of(BusinessType.TYPE_UNLISTED))
                ? Collections.emptyList() : businessTypes;
    }
}
