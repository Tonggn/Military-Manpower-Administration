package com.toggn.mma.support.fixture;

import com.toggn.mma.itp.enterprise.domain.BusinessType;
import com.toggn.mma.itp.enterprise.domain.Enterprise;
import com.toggn.mma.itp.notice.domain.*;

import java.time.LocalDate;

public class NoticeBuilder {

    private static long noticeCount = 0;

    private Notice notice;

    public NoticeBuilder(final Enterprise enterprise) {
        noticeCount += 1;

        notice = new Notice(
                "공고 제목" + noticeCount,
                "업무 내용" + noticeCount,
                enterprise.getBusinessType(),
                "복리후생 내용" + noticeCount,
                SalaryType.TYPE_07,
                "근무지 주소" + noticeCount,
                "최종학력" + noticeCount,
                "경력 구분" + noticeCount,
                ServiceStatusType.TYPE_002,
                AgentType.TYPE_1,
                enterprise.getId(),
                noticeCount * 1111,
                new NoticeDate(
                        LocalDate.of(2024, 1, 1),
                        LocalDate.of(2024, 2, 1),
                        LocalDate.of(2024, 3, 1)
                )
        );
    }

    public NoticeBuilder setTitle(final String title) {
        this.notice = new Notice(
                title,
                notice.getTask(),
                notice.getBusinessType(),
                notice.getWelfare(),
                notice.getSalaryType(),
                notice.getServiceAddress(),
                notice.getHighestEducationLevel(),
                notice.getExperienceDivision(),
                notice.getServiceStatusType(),
                notice.getAgentType(),
                notice.getEnterpriseId(),
                notice.getNoticeNumber(),
                notice.getNoticeDate()
        );
        return this;
    }

    public NoticeBuilder setTask(final String task) {
        this.notice = new Notice(
                notice.getTitle(),
                task,
                notice.getBusinessType(),
                notice.getWelfare(),
                notice.getSalaryType(),
                notice.getServiceAddress(),
                notice.getHighestEducationLevel(),
                notice.getExperienceDivision(),
                notice.getServiceStatusType(),
                notice.getAgentType(),
                notice.getEnterpriseId(),
                notice.getNoticeNumber(),
                notice.getNoticeDate()
        );
        return this;
    }

    public NoticeBuilder setBusinessType(final BusinessType businessType) {
        this.notice = new Notice(
                notice.getTitle(),
                notice.getTask(),
                businessType,
                notice.getWelfare(),
                notice.getSalaryType(),
                notice.getServiceAddress(),
                notice.getHighestEducationLevel(),
                notice.getExperienceDivision(),
                notice.getServiceStatusType(),
                notice.getAgentType(),
                notice.getEnterpriseId(),
                notice.getNoticeNumber(),
                notice.getNoticeDate()
        );
        return this;
    }

    public NoticeBuilder setWelfare(final String welfare) {
        this.notice = new Notice(
                notice.getTitle(),
                notice.getTask(),
                notice.getBusinessType(),
                welfare,
                notice.getSalaryType(),
                notice.getServiceAddress(),
                notice.getHighestEducationLevel(),
                notice.getExperienceDivision(),
                notice.getServiceStatusType(),
                notice.getAgentType(),
                notice.getEnterpriseId(),
                notice.getNoticeNumber(),
                notice.getNoticeDate()
        );
        return this;
    }

    public NoticeBuilder setSalaryType(final SalaryType salaryType) {
        this.notice = new Notice(
                notice.getTitle(),
                notice.getTask(),
                notice.getBusinessType(),
                notice.getWelfare(),
                salaryType,
                notice.getServiceAddress(),
                notice.getHighestEducationLevel(),
                notice.getExperienceDivision(),
                notice.getServiceStatusType(),
                notice.getAgentType(),
                notice.getEnterpriseId(),
                notice.getNoticeNumber(),
                notice.getNoticeDate()
        );
        return this;
    }

    public NoticeBuilder setServiceAddress(final String serviceAddress) {
        this.notice = new Notice(
                notice.getTitle(),
                notice.getTask(),
                notice.getBusinessType(),
                notice.getWelfare(),
                notice.getSalaryType(),
                serviceAddress,
                notice.getHighestEducationLevel(),
                notice.getExperienceDivision(),
                notice.getServiceStatusType(),
                notice.getAgentType(),
                notice.getEnterpriseId(),
                notice.getNoticeNumber(),
                notice.getNoticeDate()
        );
        return this;
    }

    public NoticeBuilder setHighestEducationLevel(final String highestEducationLevel) {
        this.notice = new Notice(
                notice.getTitle(),
                notice.getTask(),
                notice.getBusinessType(),
                notice.getWelfare(),
                notice.getSalaryType(),
                notice.getServiceAddress(),
                highestEducationLevel,
                notice.getExperienceDivision(),
                notice.getServiceStatusType(),
                notice.getAgentType(),
                notice.getEnterpriseId(),
                notice.getNoticeNumber(),
                notice.getNoticeDate()
        );
        return this;
    }

    public NoticeBuilder setExperienceDivision(final String experienceDivision) {
        this.notice = new Notice(
                notice.getTitle(),
                notice.getTask(),
                notice.getBusinessType(),
                notice.getWelfare(),
                notice.getSalaryType(),
                notice.getServiceAddress(),
                notice.getHighestEducationLevel(),
                experienceDivision,
                notice.getServiceStatusType(),
                notice.getAgentType(),
                notice.getEnterpriseId(),
                notice.getNoticeNumber(),
                notice.getNoticeDate()
        );
        return this;
    }

    public NoticeBuilder setServiceStatusType(final ServiceStatusType serviceStatusType) {
        this.notice = new Notice(
                notice.getTitle(),
                notice.getTask(),
                notice.getBusinessType(),
                notice.getWelfare(),
                notice.getSalaryType(),
                notice.getServiceAddress(),
                notice.getHighestEducationLevel(),
                notice.getExperienceDivision(),
                serviceStatusType,
                notice.getAgentType(),
                notice.getEnterpriseId(),
                notice.getNoticeNumber(),
                notice.getNoticeDate()
        );
        return this;
    }

    public NoticeBuilder setAgentType(final AgentType agentType) {
        this.notice = new Notice(
                notice.getTitle(),
                notice.getTask(),
                notice.getBusinessType(),
                notice.getWelfare(),
                notice.getSalaryType(),
                notice.getServiceAddress(),
                notice.getHighestEducationLevel(),
                notice.getExperienceDivision(),
                notice.getServiceStatusType(),
                agentType,
                notice.getEnterpriseId(),
                notice.getNoticeNumber(),
                notice.getNoticeDate()
        );
        return this;
    }

    public NoticeBuilder setEnterpriseId(final Long enterpriseId) {
        this.notice = new Notice(
                notice.getTitle(),
                notice.getTask(),
                notice.getBusinessType(),
                notice.getWelfare(),
                notice.getSalaryType(),
                notice.getServiceAddress(),
                notice.getHighestEducationLevel(),
                notice.getExperienceDivision(),
                notice.getServiceStatusType(),
                notice.getAgentType(),
                enterpriseId,
                notice.getNoticeNumber(),
                notice.getNoticeDate()
        );
        return this;
    }

    public NoticeBuilder setNoticeNumber(final Long noticeNumber) {
        this.notice = new Notice(
                notice.getTitle(),
                notice.getTask(),
                notice.getBusinessType(),
                notice.getWelfare(),
                notice.getSalaryType(),
                notice.getServiceAddress(),
                notice.getHighestEducationLevel(),
                notice.getExperienceDivision(),
                notice.getServiceStatusType(),
                notice.getAgentType(),
                notice.getEnterpriseId(),
                noticeNumber,
                notice.getNoticeDate()
        );
        return this;
    }

    public NoticeBuilder setCreateDate(final LocalDate createDate) {
        this.notice = new Notice(
                notice.getTitle(),
                notice.getTask(),
                notice.getBusinessType(),
                notice.getWelfare(),
                notice.getSalaryType(),
                notice.getServiceAddress(),
                notice.getHighestEducationLevel(),
                notice.getExperienceDivision(),
                notice.getServiceStatusType(),
                notice.getAgentType(),
                notice.getEnterpriseId(),
                notice.getNoticeNumber(),
                new NoticeDate(createDate, notice.getNoticeDate().getUpdatedDate(), notice.getNoticeDate().getDeadlineDate())
        );
        return this;
    }

    public NoticeBuilder setUpdateDate(final LocalDate updateDate) {
        this.notice = new Notice(
                notice.getTitle(),
                notice.getTask(),
                notice.getBusinessType(),
                notice.getWelfare(),
                notice.getSalaryType(),
                notice.getServiceAddress(),
                notice.getHighestEducationLevel(),
                notice.getExperienceDivision(),
                notice.getServiceStatusType(),
                notice.getAgentType(),
                notice.getEnterpriseId(),
                notice.getNoticeNumber(),
                new NoticeDate(notice.getNoticeDate().getCreatedDate(), updateDate, notice.getNoticeDate().getDeadlineDate())
        );
        return this;
    }

    public NoticeBuilder setDeadlineDate(final LocalDate deadlineDate) {
        this.notice = new Notice(
                notice.getTitle(),
                notice.getTask(),
                notice.getBusinessType(),
                notice.getWelfare(),
                notice.getSalaryType(),
                notice.getServiceAddress(),
                notice.getHighestEducationLevel(),
                notice.getExperienceDivision(),
                notice.getServiceStatusType(),
                notice.getAgentType(),
                notice.getEnterpriseId(),
                notice.getNoticeNumber(),
                new NoticeDate(notice.getNoticeDate().getCreatedDate(), notice.getNoticeDate().getUpdatedDate(), deadlineDate)
        );
        return this;
    }

    public Notice build() {
        return notice;
    }
}
