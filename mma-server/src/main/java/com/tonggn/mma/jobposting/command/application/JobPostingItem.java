package com.tonggn.mma.jobposting.command.application;

import com.tonggn.mma.jobposting.command.domain.AgentType;
import com.tonggn.mma.jobposting.command.domain.BusinessType;
import com.tonggn.mma.jobposting.command.domain.JobPosting;
import com.tonggn.mma.jobposting.command.domain.SalaryType;
import com.tonggn.mma.jobposting.command.domain.ServiceStatusType;
import java.time.LocalDate;

public record JobPostingItem(
    String companyName,
    String postingNumber,
    String title,
    String task,
    String welfare,
    String workAddress,
    String areaCode,
    String minEducation,
    BusinessType businessType,
    String experienceYears,
    String experienceDivision,
    SalaryType salaryType,
    ServiceStatusType serviceStatusType,
    AgentType agentType,
    Boolean available,
    String businessRegistrationNumber,
    LocalDate publishedDate,
    LocalDate closingDate,
    LocalDate modifiedDate
) {

  public JobPosting toEntity() {
    return new JobPosting(
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
