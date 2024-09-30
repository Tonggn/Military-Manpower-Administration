package com.tonggn.mma.jobposting.query;

import com.tonggn.mma.jobposting.command.domain.JobPosting;

public record JobPostingResponse(
    Long id,
    String postingNumber,
    String title,
    String companyName,
    String businessTypeName,
    String task,
    String serviceStatusTypeName,
    String agentTypeName,
    Integer minSalary,
    Integer maxSalary,
    String minEducation,
    String experienceDivision,
    String experienceYears,
    String workAddress,
    String welfare,
    String publishedDate,
    String closingDate
) {

  public static JobPostingResponse from(final JobPosting jobPosting) {
    return new JobPostingResponse(
        jobPosting.getId(),
        jobPosting.getPostingNumber(),
        jobPosting.getTitle(),
        jobPosting.getCompanyName(),
        jobPosting.getBusinessType().getName(),
        jobPosting.getTask(),
        jobPosting.getServiceStatusType().getName(),
        jobPosting.getAgentType().getName(),
        jobPosting.getSalaryType().getMinSalary(),
        jobPosting.getSalaryType().getMaxSalary(),
        jobPosting.getMinEducation(),
        jobPosting.getExperienceDivision(),
        jobPosting.getExperienceYears(),
        jobPosting.getWorkAddress(),
        jobPosting.getWelfare(),
        jobPosting.getPublishedDate().toString(),
        jobPosting.getClosingDate().toString()
    );
  }
}
