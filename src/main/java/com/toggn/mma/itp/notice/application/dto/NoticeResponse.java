package com.toggn.mma.itp.notice.application.dto;

import com.toggn.mma.itp.enterprise.domain.Enterprise;
import com.toggn.mma.itp.notice.domain.Notice;

import java.time.LocalDate;

public record NoticeResponse(
        String title,
        String task,
        String businessType,
        String welfare,
        int minimumSalary,
        int maximumSalary,
        String serviceAddress,
        String highestEducationLevel,
        String experienceDivision,
        String serviceStatusType,
        String agentType,
        EnterpriseResponse enterprise,
        Long noticeNumber,
        LocalDate createdDate,
        LocalDate updatedDate,
        LocalDate deadlineDate
) {

    public static NoticeResponse from(final Notice notice, final Enterprise enterprise) {
        return new NoticeResponse(
                notice.getTitle(),
                notice.getTask(),
                notice.getBusinessType().getName(),
                notice.getWelfare(),
                notice.getSalaryType().getMinimumSalary(),
                notice.getSalaryType().getMaximumSalary(),
                notice.getServiceAddress(),
                notice.getHighestEducationLevel(),
                notice.getExperienceDivision(),
                notice.getServiceStatusType().getName(),
                notice.getAgentType().getName(),
                EnterpriseResponse.from(enterprise),
                notice.getNoticeNumber(),
                notice.getNoticeDate().getCreatedDate(),
                notice.getNoticeDate().getUpdatedDate(),
                notice.getNoticeDate().getDeadlineDate()
        );
    }

    public record EnterpriseResponse(
            String name,
            String businessType,
            String websiteUrl,
            String address
    ) {

        public static EnterpriseResponse from(final Enterprise enterprise) {
            return new EnterpriseResponse(
                    enterprise.getName(),
                    enterprise.getBusinessType().getName(),
                    enterprise.getWebsiteUrl(),
                    enterprise.getAddress()
            );
        }
    }
}
