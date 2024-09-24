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
}
