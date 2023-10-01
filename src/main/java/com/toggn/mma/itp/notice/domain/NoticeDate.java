package com.toggn.mma.itp.notice.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Objects;

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

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final NoticeDate that = (NoticeDate) o;
        return Objects.equals(createdDate, that.createdDate) &&
                Objects.equals(updatedDate, that.updatedDate) &&
                Objects.equals(deadlineDate, that.deadlineDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(createdDate, updatedDate, deadlineDate);
    }
}
