package com.toggn.mma.itp.notice.domain.repository;

import com.toggn.mma.itp.enterprise.domain.BusinessType;
import com.toggn.mma.itp.notice.domain.AgentType;
import com.toggn.mma.itp.notice.domain.Notice;
import com.toggn.mma.itp.notice.domain.ServiceStatusType;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class NoticeSpecification {

    private NoticeSpecification() {
    }

    public static Specification<Notice> keywordContains(final String keyword) {
        return (root, query, builder) -> builder.like(root.get("title"), "%" + keyword + "%");
    }

    public static Specification<Notice> serviceStatusTypeEquals(final ServiceStatusType serviceStatusType) {
        return (root, query, builder) -> builder.equal(root.get("serviceStatusType"), serviceStatusType);
    }


    public static Specification<Notice> agentTypeEquals(final AgentType agentType) {
        return (root, query, builder) -> builder.equal(root.get("agentType"), agentType);
    }

    public static Specification<Notice> businessTypesIn(final List<BusinessType> businessTypes) {
        return (root, query, builder) -> root.get("businessType").in(businessTypes);
    }

    public static Specification<Notice> serviceAddressKeywordContains(final String serviceAddressKeyword) {
        return (root, query, builder) -> builder.like(root.get("serviceAddress"), "%" + serviceAddressKeyword + "%");
    }
}
