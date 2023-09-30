package com.toggn.mma.itp.notice.domain;

import com.toggn.mma.base.BaseDatetimeEntity;
import com.toggn.mma.itp.enterprise.domain.BusinessCode;
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
    @Column(name = "business_code", length = 20, nullable = false)
    private BusinessCode business;

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
            final BusinessCode business,
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
}
