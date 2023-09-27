package com.toggn.mma.itp.notice.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NoticeDate {

    @Column(nullable = false)
    private LocalDate createdDate;

    @Column(nullable = false)
    private LocalDate updatedDate;

    @Column(nullable = false)
    private LocalDate deadlineDate;

    public NoticeDate(final LocalDate createdDate, final LocalDate updatedDate, final LocalDate deadlineDate) {
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.deadlineDate = deadlineDate;
    }
}
