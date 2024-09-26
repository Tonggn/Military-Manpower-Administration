package com.tonggn.mma.jobposting.command.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JobPosting {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  // @Column(nullable = false)
  // private Long companyId;
  @Column(nullable = false)
  private String companyName;

  @Column(nullable = false, unique = true)
  private String postingNumber;

  @Column(nullable = false)
  private String title;

  private String task;

  @Column(nullable = false)
  private String welfare;

  @Column(nullable = false)
  private String workAddress;
  @Column(nullable = false)
  private String areaCode;

  @Column(nullable = false)
  private String minEducation;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private BusinessType businessType;

  @Column(nullable = false)
  private String experienceYears;
  @Column(nullable = false)
  private String experienceDivision;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private SalaryType salaryType;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private ServiceStatusType serviceStatusType;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private AgentType agentType;

  @Column(nullable = false)
  private Boolean available;

  @Column(nullable = false)
  private String businessRegistrationNumber;

  private LocalDate publishedDate;
  @Column(nullable = false)
  private LocalDate closingDate;
  private LocalDate modifiedDate;

  @CreatedDate
  @Column(updatable = false)
  private LocalDateTime createdAt;
  @LastModifiedDate
  private LocalDateTime updatedAt;

  public JobPosting(final String companyName, final String postingNumber, final String title,
      final String task, final String welfare, final String workAddress, final String areaCode,
      final String minEducation, final BusinessType businessType, final String experienceYears,
      final String experienceDivision, final SalaryType salaryType,
      final ServiceStatusType serviceStatusType, final AgentType agentType, final Boolean available,
      final String businessRegistrationNumber, final LocalDate publishedDate,
      final LocalDate closingDate, final LocalDate modifiedDate) {
    this.companyName = companyName;
    this.postingNumber = postingNumber;
    this.title = title;
    this.task = task;
    this.welfare = welfare;
    this.workAddress = workAddress;
    this.areaCode = areaCode;
    this.minEducation = minEducation;
    this.businessType = businessType;
    this.experienceYears = experienceYears;
    this.experienceDivision = experienceDivision;
    this.salaryType = salaryType;
    this.serviceStatusType = serviceStatusType;
    this.agentType = agentType;
    this.available = available;
    this.businessRegistrationNumber = businessRegistrationNumber;
    this.publishedDate = publishedDate;
    this.closingDate = closingDate;
    this.modifiedDate = modifiedDate;
  }

  public void update(final JobPosting newPosting) {
    this.companyName = newPosting.getCompanyName();
    this.title = newPosting.getTitle();
    this.task = newPosting.getTask();
    this.welfare = newPosting.getWelfare();
    this.workAddress = newPosting.getWorkAddress();
    this.areaCode = newPosting.getAreaCode();
    this.minEducation = newPosting.getMinEducation();
    this.businessType = newPosting.getBusinessType();
    this.experienceYears = newPosting.getExperienceYears();
    this.experienceDivision = newPosting.getExperienceDivision();
    this.salaryType = newPosting.getSalaryType();
    this.serviceStatusType = newPosting.getServiceStatusType();
    this.agentType = newPosting.getAgentType();
    this.available = newPosting.getAvailable();
    this.businessRegistrationNumber = newPosting.getBusinessRegistrationNumber();
    this.publishedDate = newPosting.getPublishedDate();
    this.closingDate = newPosting.getClosingDate();
    this.modifiedDate = newPosting.getModifiedDate();
  }
}
