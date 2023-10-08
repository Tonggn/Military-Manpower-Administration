package com.toggn.mma.support.fixture;

import com.toggn.mma.itp.enterprise.domain.Enterprise;
import com.toggn.mma.itp.enterprise.parser.dto.EnterpriseParseResponse;
import com.toggn.mma.itp.notice.domain.Notice;
import com.toggn.mma.itp.notice.parser.dto.NoticeParseResponse;

public class ParseResponseConverter {

    public static EnterpriseParseResponse toEnterpriseParseResponse(final Enterprise enterprise) {
        return new EnterpriseParseResponse(
                enterprise.getName(),
                enterprise.getBusinessType().getCode(),
                enterprise.getWebsiteUrl(),
                enterprise.getAddress()
        );
    }

    public static NoticeParseResponse toNoticeParseResponse(final Notice notice, final Enterprise enterprise) {
        return new NoticeParseResponse(
                notice.getTitle(),
                notice.getTask(),
                notice.getBusinessType().getCode(),
                notice.getWelfare(),
                notice.getSalaryType().getCode(),
                notice.getServiceAddress(),
                notice.getHighestEducationLevel(),
                notice.getExperienceDivision(),
                notice.getServiceStatusType().getCode(),
                notice.getAgentType().getCode(),
                enterprise.getName(),
                notice.getNoticeNumber(),
                notice.getNoticeDate().getCreatedDate(),
                notice.getNoticeDate().getUpdatedDate(),
                notice.getNoticeDate().getDeadlineDate()
        );
    }
}
