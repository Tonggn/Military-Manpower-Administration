package com.toggn.mma.itp.notice.domain.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.toggn.mma.itp.enterprise.domain.BusinessType;
import com.toggn.mma.itp.notice.domain.AgentType;
import com.toggn.mma.itp.notice.domain.Notice;
import com.toggn.mma.itp.notice.domain.ServiceStatusType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.toggn.mma.itp.notice.domain.QNotice.notice;

@RequiredArgsConstructor
public class NoticeRepositoryImpl implements NoticeRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Notice> findByFiltering(
            final String title,
            final ServiceStatusType serviceStatusType,
            final AgentType agentType,
            final String serviceAddress,
            final String enterpriseName,
            final List<BusinessType> businessTypes,
            final Pageable pageable
    ) {
        final List<Notice> notices = queryFactory.selectFrom(notice)
                .where(titleContains(title),
                        serviceStatusTypeEq(serviceStatusType),
                        agentTypeEq(agentType),
                        serviceAddressContains(serviceAddress),
                        enterpriseNameContains(enterpriseName),
                        businessTypeIn(businessTypes))
                .leftJoin(notice.enterprise)
                .fetchJoin()
                .distinct()
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(notice.noticeDate.createdDate.desc(), notice.createdAt.desc())
                .fetch();

        final Long totalCount = queryFactory.select(notice.count())
                .from(notice)
                .where(titleContains(title),
                        serviceStatusTypeEq(serviceStatusType),
                        agentTypeEq(agentType),
                        serviceAddressContains(serviceAddress),
                        enterpriseNameContains(enterpriseName),
                        businessTypeIn(businessTypes))
                .fetchOne();

        return new PageImpl<>(notices, pageable, totalCount);
    }

    private BooleanExpression titleContains(final String title) {
        return StringUtils.hasText(title) ? notice.title.contains(title) : null;
    }

    private BooleanExpression serviceStatusTypeEq(final ServiceStatusType serviceStatusType) {
        return serviceStatusType != null && serviceStatusType.isValid()
                ? notice.serviceStatusType.eq(serviceStatusType) : null;
    }

    private BooleanExpression agentTypeEq(final AgentType agentType) {
        return agentType != null && agentType.isValid() ? notice.agentType.eq(agentType) : null;
    }

    private BooleanExpression serviceAddressContains(final String serviceAddress) {
        return StringUtils.hasText(serviceAddress) ? notice.serviceAddress.contains(serviceAddress) : null;
    }

    private BooleanExpression enterpriseNameContains(final String enterpriseName) {
        return StringUtils.hasText(enterpriseName) ? notice.enterprise.name.contains(enterpriseName) : null;
    }

    private BooleanExpression businessTypeIn(final List<BusinessType> businessTypes) {
        return businessTypes != null && !businessTypes.isEmpty() ? notice.businessType.in(businessTypes) : null;
    }
}
