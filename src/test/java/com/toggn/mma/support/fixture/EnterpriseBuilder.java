package com.toggn.mma.support.fixture;

import com.toggn.mma.itp.enterprise.domain.BusinessType;
import com.toggn.mma.itp.enterprise.domain.Enterprise;

public class EnterpriseBuilder {

    private static long counter = 0;

    private Enterprise enterprise;

    public EnterpriseBuilder() {
        counter += 1;

        enterprise = new Enterprise(
                "업체명" + counter,
                BusinessType.TYPE_11111,
                "업체 홈페이지" + counter,
                "업체 주소지" + counter
        );
    }

    public EnterpriseBuilder setName(final String name) {
        enterprise = new Enterprise(
                name,
                enterprise.getBusinessType(),
                enterprise.getWebsiteUrl(),
                enterprise.getAddress()
        );
        return this;
    }

    public EnterpriseBuilder setBusinessType(final BusinessType businessType) {
        enterprise = new Enterprise(
                enterprise.getName(),
                businessType,
                enterprise.getWebsiteUrl(),
                enterprise.getAddress()
        );
        return this;
    }

    public EnterpriseBuilder setWebsiteUrl(final String websiteUrl) {
        enterprise = new Enterprise(
                enterprise.getName(),
                enterprise.getBusinessType(),
                websiteUrl,
                enterprise.getAddress()
        );
        return this;
    }

    public EnterpriseBuilder setAddress(final String address) {
        enterprise = new Enterprise(
                enterprise.getName(),
                enterprise.getBusinessType(),
                enterprise.getWebsiteUrl(),
                address
        );
        return this;
    }

    public Enterprise build() {
        return enterprise;
    }
}
