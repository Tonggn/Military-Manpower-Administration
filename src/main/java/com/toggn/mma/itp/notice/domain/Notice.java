package com.toggn.mma.itp.notice.domain;

import com.toggn.mma.base.BaseDatetimeEntity;
import com.toggn.mma.itp.enterprise.domain.BusinessType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notice extends BaseDatetimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String task;

    @Enumerated(EnumType.STRING)
    @Column(name = "business_type", length = 20, nullable = false)
    private BusinessType businessType;

    @Column(nullable = false)
    private String welfare;

    @Enumerated(EnumType.STRING)
    @Column(name = "salary_type", length = 20, nullable = false)
    private SalaryType salaryType;

    @Column(length = 1024, nullable = false)
    private String serviceAddress;

    @Column(nullable = false)
    private String highestEducationLevel;

    @Column(nullable = false)
    private String experienceDivision;

    @Enumerated(EnumType.STRING)
    @Column(name = "service_status_type", length = 20, nullable = false)
    private ServiceStatusType serviceStatusType;

    @Enumerated(EnumType.STRING)
    @Column(name = "agent_type", length = 20, nullable = false)
    private AgentType agentType;

    @Column(nullable = false)
    private Long enterpriseId;

    @Column(nullable = false, unique = true)
    private Long noticeNumber;

    @Embedded
    private NoticeDate noticeDate;

    public Notice(
            final String title,
            final String task,
            final BusinessType businessType,
            final String welfare,
            final SalaryType salaryType,
            final String serviceAddress,
            final String highestEducationLevel,
            final String experienceDivision,
            final ServiceStatusType serviceStatusType,
            final AgentType agentType,
            final Long enterpriseId,
            final Long noticeNumber,
            final NoticeDate noticeDate
    ) {
        this.title = title;
        this.task = task;
        this.businessType = businessType;
        this.welfare = welfare;
        this.salaryType = salaryType;
        this.serviceAddress = serviceAddress;
        this.highestEducationLevel = highestEducationLevel;
        this.experienceDivision = experienceDivision;
        this.serviceStatusType = serviceStatusType;
        this.agentType = agentType;
        this.enterpriseId = enterpriseId;
        this.noticeNumber = noticeNumber;
        this.noticeDate = noticeDate;
    }

    public boolean isUpdatable(final Notice notice) {
        return this.noticeNumber.equals(notice.getNoticeNumber()) && (
                !this.title.equals(notice.getTitle()) ||
                        !this.task.equals(notice.getTask()) ||
                        !this.businessType.equals(notice.getBusinessType()) ||
                        !this.welfare.equals(notice.getWelfare()) ||
                        !this.salaryType.equals(notice.getSalaryType()) ||
                        !this.serviceAddress.equals(notice.getServiceAddress()) ||
                        !this.highestEducationLevel.equals(notice.getHighestEducationLevel()) ||
                        !this.experienceDivision.equals(notice.getExperienceDivision()) ||
                        !this.serviceStatusType.equals(notice.getServiceStatusType()) ||
                        !this.agentType.equals(notice.getAgentType()) ||
                        !this.noticeDate.equals(notice.getNoticeDate())
        );
    }

    public void update(final Notice notice) {
        this.title = notice.getTitle();
        this.task = notice.getTask();
        this.businessType = notice.getBusinessType();
        this.welfare = notice.getWelfare();
        this.salaryType = notice.getSalaryType();
        this.serviceAddress = notice.getServiceAddress();
        this.highestEducationLevel = notice.getHighestEducationLevel();
        this.experienceDivision = notice.getExperienceDivision();
        this.serviceStatusType = notice.getServiceStatusType();
        this.agentType = notice.getAgentType();
        this.noticeDate = notice.getNoticeDate();
    }
}
