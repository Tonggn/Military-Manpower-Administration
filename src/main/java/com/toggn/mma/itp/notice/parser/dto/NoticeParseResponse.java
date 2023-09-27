package com.toggn.mma.itp.notice.parser.dto;

import java.time.LocalDate;

public record NoticeParseResponse(
        String title,
        String task,
        String businessCode,
        String welfare,
        String salaryCode,
        String serviceAddress,
        String highestEducationLevel,
        String experienceDivision,
        String serviceStatusCode,
        String agentCode,
        String enterpriseName,
        Long noticeNumber,
        LocalDate createdDate,
        LocalDate updatedDate,
        LocalDate deadlineDate
) {
}
