package com.toggn.mma.support.fixture;

import com.toggn.mma.itp.enterprise.domain.BusinessType;
import com.toggn.mma.itp.enterprise.domain.Enterprise;
import com.toggn.mma.itp.enterprise.parser.dto.EnterpriseParseResponse;

public class EnterpriseFixture {

    public static final Enterprise enterprise1 = new Enterprise("기업1", BusinessType.TYPE_11111, "홈페이지주소1", "주소1");
    public static final Enterprise enterprise2 = new Enterprise("기업2", BusinessType.TYPE_11102, "홈페이지주소2", "주소2");
    public static final Enterprise enterprise3 = new Enterprise("기업3", BusinessType.TYPE_11112, "홈페이지주소3", "주소3");

    public static EnterpriseParseResponse convertToEnterpriseParseResponse(final Enterprise enterprise) {
        return new EnterpriseParseResponse(
                enterprise.getName(),
                enterprise.getBusiness().getCode(),
                enterprise.getWebsiteUrl(),
                enterprise.getAddress()
        );
    }
}
