package com.tonggn.mma.jobposting.controller;

import com.tonggn.mma.jobposting.command.domain.AgentType;
import com.tonggn.mma.jobposting.command.domain.BusinessType;
import com.tonggn.mma.jobposting.command.domain.ServiceStatusType;
import com.tonggn.mma.jobposting.query.BusinessTypeResponse;
import com.tonggn.mma.jobposting.query.JobPostingDao;
import com.tonggn.mma.jobposting.query.JobPostingResponse;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/jobposting")
@RequiredArgsConstructor
public class JobPostingController {

  private final JobPostingDao jobPostingDao;

  @GetMapping("/business-types")
  public List<BusinessTypeResponse> getBusinessTypes() {
    return Arrays.stream(BusinessType.values())
        .map(business -> new BusinessTypeResponse(business.getName(), business.getCode().get(0)))
        .toList();
  }

  @GetMapping
  public List<JobPostingResponse> getJobPostings(
      @RequestParam(defaultValue = "1") final Integer page,
      @RequestParam(required = false) final String keyword,
      @RequestParam(required = false) final ServiceStatusType serviceStatusType,
      @RequestParam(required = false) final AgentType agentType,
      @RequestParam(required = false) final String companyNameKeyword,
      @RequestParam(required = false) final List<BusinessType> businessTypes
  ) {
    return jobPostingDao.getJobPostings(page, keyword, serviceStatusType, agentType,
        companyNameKeyword, businessTypes);
  }
}
