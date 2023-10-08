package com.toggn.mma.support.fixture;

import com.toggn.mma.itp.enterprise.domain.BusinessType;
import com.toggn.mma.itp.notice.application.dto.NoticeFilterRequest;
import com.toggn.mma.itp.notice.domain.AgentType;
import com.toggn.mma.itp.notice.domain.ServiceStatusType;

import java.util.List;

public class NoticeFilterRequestBuilder {

    private NoticeFilterRequest noticeFilterRequest;

    public NoticeFilterRequestBuilder() {
        noticeFilterRequest = new NoticeFilterRequest(null, null, null, null, null);
    }

    public NoticeFilterRequestBuilder setKeyword(final String keyword) {
        noticeFilterRequest = new NoticeFilterRequest(keyword, noticeFilterRequest.serviceStatusType(), noticeFilterRequest.agentType(), noticeFilterRequest.serviceAddressKeyword(), noticeFilterRequest.businessTypes());
        return this;
    }

    public NoticeFilterRequestBuilder setServiceStatusType(final ServiceStatusType serviceStatusType) {
        noticeFilterRequest = new NoticeFilterRequest(noticeFilterRequest.keyword(), serviceStatusType, noticeFilterRequest.agentType(), noticeFilterRequest.serviceAddressKeyword(), noticeFilterRequest.businessTypes());
        return this;
    }

    public NoticeFilterRequestBuilder setAgentType(final AgentType agentType) {
        noticeFilterRequest = new NoticeFilterRequest(noticeFilterRequest.keyword(), noticeFilterRequest.serviceStatusType(), agentType, noticeFilterRequest.serviceAddressKeyword(), noticeFilterRequest.businessTypes());
        return this;
    }

    public NoticeFilterRequestBuilder setServiceAddressKeyword(final String serviceAddressKeyword) {
        noticeFilterRequest = new NoticeFilterRequest(noticeFilterRequest.keyword(), noticeFilterRequest.serviceStatusType(), noticeFilterRequest.agentType(), serviceAddressKeyword, noticeFilterRequest.businessTypes());
        return this;
    }

    public NoticeFilterRequestBuilder setBusinessTypes(final List<BusinessType> businessTypes) {
        noticeFilterRequest = new NoticeFilterRequest(noticeFilterRequest.keyword(), noticeFilterRequest.serviceStatusType(), noticeFilterRequest.agentType(), noticeFilterRequest.serviceAddressKeyword(), businessTypes);
        return this;
    }

    public NoticeFilterRequest build() {
        return noticeFilterRequest;
    }
}
