package com.toggn.mma.itp.notice.domain.repository;

import com.toggn.mma.itp.notice.domain.Notice;
import org.springframework.data.jpa.domain.Specification;

public class NoticeSpecification {

    private NoticeSpecification() {
    }

    public static Specification<Notice> keywordContains(final String keyword) {
        return (root, query, builder) -> builder.like(root.get("title"), "%" + keyword + "%");
    }
}
