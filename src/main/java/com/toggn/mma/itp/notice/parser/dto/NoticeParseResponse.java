package com.toggn.mma.itp.notice.parser.dto;

import java.time.LocalDate;

public record NoticeParseResponse(
        String title,
        String task,
        String businessTypeCode,
        String welfare,
        String salaryTypeCode,
        String serviceAddress,
        String highestEducationLevel,
        String experienceDivision,
        String serviceStatusTypeCode,
        String agentTypeCode,
        String enterpriseName,
        Long noticeNumber,
        LocalDate createdDate,
        LocalDate updatedDate,
        LocalDate deadlineDate
) {
}
