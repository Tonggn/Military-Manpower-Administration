package com.toggn.mma.itp.notice.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ServiceStatusTypeTest {

    @ParameterizedTest
    @EmptySource
    @NullSource
    @DisplayName("from(): 역종코드가 존재하지 않을 경우 TYPE_UNLISTED를 반환한다.")
    void 역종_코드가_존재하지_않을_경우_TYPE_UNLISTED를_반환한다(final String invalidServiceStatusTypeCode) {
        // given
        // when
        final ServiceStatusType actual = ServiceStatusType.from(invalidServiceStatusTypeCode);

        // then
        assertEquals(ServiceStatusType.TYPE_UNLISTED, actual);
    }
}
