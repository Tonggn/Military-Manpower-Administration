package com.toggn.mma.itp.enterprise.parser.dto;

import java.util.Objects;

public record EnterpriseParseResponse(
        String name,
        String businessCode,
        String websiteUrl,
        String address
) {

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final EnterpriseParseResponse that = (EnterpriseParseResponse) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
