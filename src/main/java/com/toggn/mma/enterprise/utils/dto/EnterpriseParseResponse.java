package com.toggn.mma.enterprise.utils.dto;

public record EnterpriseParseResponse(
        String name,
        String businessCode,
        String scaleCode,
        String websiteUrl,
        String address
) {
}
