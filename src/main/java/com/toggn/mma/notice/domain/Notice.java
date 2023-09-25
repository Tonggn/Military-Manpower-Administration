package com.toggn.mma.notice.domain;

import com.toggn.mma.enterprise.domain.BusinessCode;
import jakarta.persistence.*;

@Entity
public class Notice {

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

    @Column(nullable = false)
    private Long noticeNumber;

    @Embedded
    private NoticeDate noticeDate;
}
