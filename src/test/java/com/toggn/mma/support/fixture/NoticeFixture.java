package com.toggn.mma.support.fixture;

import com.toggn.mma.itp.enterprise.domain.Enterprise;
import com.toggn.mma.itp.notice.domain.*;
import com.toggn.mma.itp.notice.parser.dto.NoticeParseResponse;

import java.time.LocalDate;

public class NoticeFixture {

    private static long noticeCount = 0;

    public static Notice getNotice(
            final Enterprise enterprise,
            final SalaryCode salaryCode,
            final ServiceStatusCode serviceStatusCode,
            final AgentCode agentCode,
            final LocalDate createdDate,
            final LocalDate updatedDate,
            final LocalDate deadlineDate
    ) {
        noticeCount++;

        return new Notice(
                "공고 제목" + noticeCount,
                "업무 내용" + noticeCount,
                enterprise.getBusiness(),
                "복리후생 내용" + noticeCount,
                salaryCode,
                "근무지 주소" + noticeCount,
                "최종학력" + noticeCount,
                "경력 구분" + noticeCount,
                serviceStatusCode,
                agentCode,
                enterprise.getId(),
                noticeCount * 1111,
                new NoticeDate(createdDate, updatedDate, deadlineDate)
        );
    }

    public static NoticeParseResponse convertToNoticeParseResponse(final Notice notice, final Enterprise enterprise) {
        return new NoticeParseResponse(
                notice.getTitle(),
                notice.getTask(),
                notice.getBusiness().getCode(),
                notice.getWelfare(),
                notice.getSalary().getCode(),
                notice.getServiceAddress(),
                notice.getHighestEducationLevel(),
                notice.getExperienceDivision(),
                notice.getServiceStatus().getCode(),
                notice.getAgent().getCode(),
                enterprise.getName(),
                notice.getNoticeNumber(),
                notice.getNoticeDate().getCreatedDate(),
                notice.getNoticeDate().getUpdatedDate(),
                notice.getNoticeDate().getDeadlineDate()
        );
    }
}
