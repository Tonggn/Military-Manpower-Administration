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
    private BusinessType business;

    @Column(nullable = false)
    private String welfare;

    @Enumerated(EnumType.STRING)
    @Column(name = "salary_code", length = 20, nullable = false)
    private SalaryCode salary;

    @Column(length = 1024, nullable = false)
    private String serviceAddress;

    @Column(nullable = false)
    private String highestEducationLevel;

    @Column(nullable = false)
    private String experienceDivision;

    @Enumerated(EnumType.STRING)
    @Column(name = "service_status_code", length = 20, nullable = false)
    private ServiceStatusCode serviceStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "agent_code", length = 20, nullable = false)
    private AgentCode agent;

    @Column(nullable = false)
    private Long enterpriseId;

    @Column(nullable = false, unique = true)
    private Long noticeNumber;

    @Embedded
    private NoticeDate noticeDate;

    public Notice(
            final String title,
            final String task,
            final BusinessType business,
            final String welfare,
            final SalaryCode salary,
            final String serviceAddress,
            final String highestEducationLevel,
            final String experienceDivision,
            final ServiceStatusCode serviceStatus,
            final AgentCode agent,
            final Long enterpriseId,
            final Long noticeNumber,
            final NoticeDate noticeDate
    ) {
        this.title = title;
        this.task = task;
        this.business = business;
        this.welfare = welfare;
        this.salary = salary;
        this.serviceAddress = serviceAddress;
        this.highestEducationLevel = highestEducationLevel;
        this.experienceDivision = experienceDivision;
        this.serviceStatus = serviceStatus;
        this.agent = agent;
        this.enterpriseId = enterpriseId;
        this.noticeNumber = noticeNumber;
        this.noticeDate = noticeDate;
    }

    public boolean isUpdatable(final Notice notice) {
        return this.noticeNumber.equals(notice.getNoticeNumber()) && (
                !this.title.equals(notice.getTitle()) ||
                        !this.task.equals(notice.getTask()) ||
                        !this.business.equals(notice.getBusiness()) ||
                        !this.welfare.equals(notice.getWelfare()) ||
                        !this.salary.equals(notice.getSalary()) ||
                        !this.serviceAddress.equals(notice.getServiceAddress()) ||
                        !this.highestEducationLevel.equals(notice.getHighestEducationLevel()) ||
                        !this.experienceDivision.equals(notice.getExperienceDivision()) ||
                        !this.serviceStatus.equals(notice.getServiceStatus()) ||
                        !this.agent.equals(notice.getAgent()) ||
                        !this.noticeDate.equals(notice.getNoticeDate())
        );
    }

    public void update(final Notice notice) {
        this.title = notice.getTitle();
        this.task = notice.getTask();
        this.business = notice.getBusiness();
        this.welfare = notice.getWelfare();
        this.salary = notice.getSalary();
        this.serviceAddress = notice.getServiceAddress();
        this.highestEducationLevel = notice.getHighestEducationLevel();
        this.experienceDivision = notice.getExperienceDivision();
        this.serviceStatus = notice.getServiceStatus();
        this.agent = notice.getAgent();
        this.noticeDate = notice.getNoticeDate();
    }
}
