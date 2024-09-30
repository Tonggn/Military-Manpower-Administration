package com.tonggn.mma.jobposting.query;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tonggn.mma.jobposting.command.domain.AgentType;
import com.tonggn.mma.jobposting.command.domain.BusinessType;
import com.tonggn.mma.jobposting.command.domain.QJobPosting;
import com.tonggn.mma.jobposting.command.domain.ServiceStatusType;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JobPostingDao {

  public static final int PAGE_SIZE = 30;
  private final JPAQueryFactory queryFactory;

  public List<JobPostingResponse> getJobPostings(
      final Integer page, final String keyword, final ServiceStatusType serviceStatusType,
      final AgentType agentType,
      final String companyNameKeyword, final List<BusinessType> businessTypes
  ) {
    final QJobPosting jobPosting = QJobPosting.jobPosting;
    final BooleanBuilder builder = new BooleanBuilder();

    if (keyword != null) {
      builder.and(jobPosting.title.contains(keyword));
    }

    if (serviceStatusType != null) {
      builder.and(jobPosting.serviceStatusType.eq(serviceStatusType));
    }

    if (agentType != null) {
      builder.and(jobPosting.agentType.eq(agentType));
    }

    if (companyNameKeyword != null) {
      builder.and(jobPosting.companyName.contains(companyNameKeyword));
    }

    if (businessTypes != null) {
      builder.and(jobPosting.businessType.in(businessTypes));
    }

    final int offset = (page - 1) * PAGE_SIZE;
    return queryFactory.selectFrom(jobPosting)
        .where(builder)
        .offset(offset)
        .limit(PAGE_SIZE)
        .fetch()
        .stream()
        .map(JobPostingResponse::from)
        .toList();
  }
}
