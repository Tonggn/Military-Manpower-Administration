package com.toggn.mma.itp.enterprise.parser.dto;

public record EnterpriseParseResponse(
        String name,
        String businessCode,
        String scaleCode,
        String websiteUrl,
        String address
) {
}
