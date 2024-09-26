package com.tonggn.mma.jobposting.command.application;

import com.tonggn.mma.jobposting.command.domain.AgentType;
import com.tonggn.mma.jobposting.command.domain.BusinessType;
import com.tonggn.mma.jobposting.command.domain.SalaryType;
import com.tonggn.mma.jobposting.command.domain.ServiceStatusType;
import java.time.LocalDate;

public class JobPostingItemBuilder {

  private static int count = 0;

  private String companyName;
  private String postingNumber;
  private String title;
  private String task;
  private String welfare;
  private String workAddress;
  private String areaCode;
  private String minEducation;
  private BusinessType businessType;
  private String experienceYears;
  private String experienceDivision;
  private SalaryType salaryType;
  private ServiceStatusType serviceStatusType;
  private AgentType agentType;
  private Boolean available;
  private String businessRegistrationNumber;
  private LocalDate publishedDate;
  private LocalDate closingDate;
  private LocalDate modifiedDate;

  public JobPostingItemBuilder() {
    this.companyName = "companyName" + count;
    this.postingNumber = "postingNumber" + count;
    this.title = "title" + count;
    this.task = "task" + count;
    this.welfare = "welfare" + count;
    this.workAddress = "workAddress" + count;
    this.areaCode = "areaCode" + count;
    this.minEducation = "minEducation" + count;
    this.businessType = BusinessType.values()[count % BusinessType.values().length];
    this.experienceYears = "experienceYears" + count;
    this.experienceDivision = "experienceDivision" + count;
    this.salaryType = SalaryType.values()[count % SalaryType.values().length];
    this.serviceStatusType = ServiceStatusType.values()[count % ServiceStatusType.values().length];
    this.agentType = AgentType.values()[count % AgentType.values().length];
    this.available = true;
    this.businessRegistrationNumber = "businessRegistrationNumber" + count;
    this.publishedDate = LocalDate.now();
    this.closingDate = LocalDate.now();
    this.modifiedDate = LocalDate.now();
    count++;
  }

  public JobPostingItemBuilder companyName(String companyName) {
    this.companyName = companyName;
    return this;
  }

  public JobPostingItemBuilder postingNumber(String postingNumber) {
    this.postingNumber = postingNumber;
    return this;
  }

  public JobPostingItemBuilder title(String title) {
    this.title = title;
    return this;
  }

  public JobPostingItemBuilder task(String task) {
    this.task = task;
    return this;
  }

  public JobPostingItemBuilder welfare(String welfare) {
    this.welfare = welfare;
    return this;
  }

  public JobPostingItemBuilder workAddress(String workAddress) {
    this.workAddress = workAddress;
    return this;
  }

  public JobPostingItemBuilder areaCode(String areaCode) {
    this.areaCode = areaCode;
    return this;
  }

  public JobPostingItemBuilder minEducation(String minEducation) {
    this.minEducation = minEducation;
    return this;
  }

  public JobPostingItemBuilder businessType(BusinessType businessType) {
    this.businessType = businessType;
    return this;
  }

  public JobPostingItemBuilder experienceYears(String experienceYears) {
    this.experienceYears = experienceYears;
    return this;
  }

  public JobPostingItemBuilder experienceDivision(String experienceDivision) {
    this.experienceDivision = experienceDivision;
    return this;
  }

  public JobPostingItemBuilder salaryType(SalaryType salaryType) {
    this.salaryType = salaryType;
    return this;
  }

  public JobPostingItemBuilder serviceStatusType(ServiceStatusType serviceStatusType) {
    this.serviceStatusType = serviceStatusType;
    return this;
  }

  public JobPostingItemBuilder agentType(AgentType agentType) {
    this.agentType = agentType;
    return this;
  }

  public JobPostingItemBuilder available(Boolean available) {
    this.available = available;
    return this;
  }

  public JobPostingItemBuilder businessRegistrationNumber(String businessRegistrationNumber) {
    this.businessRegistrationNumber = businessRegistrationNumber;
    return this;
  }

  public JobPostingItemBuilder publishedDate(LocalDate publishedDate) {
    this.publishedDate = publishedDate;
    return this;
  }

  public JobPostingItemBuilder closingDate(LocalDate closingDate) {
    this.closingDate = closingDate;
    return this;
  }

  public JobPostingItemBuilder modifiedDate(LocalDate modifiedDate) {
    this.modifiedDate = modifiedDate;
    return this;
  }

  public JobPostingItem build() {
    return new JobPostingItem(
        companyName,
        postingNumber,
        title,
        task,
        welfare,
        workAddress,
        areaCode,
        minEducation,
        businessType,
        experienceYears,
        experienceDivision,
        salaryType,
        serviceStatusType,
        agentType,
        available,
        businessRegistrationNumber,
        publishedDate,
        closingDate,
        modifiedDate
    );
  }
}
