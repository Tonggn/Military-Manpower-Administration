package com.toggn.mma.notice.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.time.LocalDate;

@Embeddable
public class NoticeDate {

    @Column(nullable = false)
    private LocalDate createdDate;

    @Column(nullable = false)
    private LocalDate updatedDate;

    @Column(nullable = false)
    private LocalDate deadlineDate;
}
