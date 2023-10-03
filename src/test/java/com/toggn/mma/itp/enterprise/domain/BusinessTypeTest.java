package com.toggn.mma.itp.enterprise.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;

import static com.toggn.mma.itp.enterprise.domain.BusinessType.TYPE_UNLISTED;
import static org.assertj.core.api.Assertions.assertThat;

class BusinessTypeTest {

    @ParameterizedTest
    @EmptySource
    @NullSource
    @DisplayName("from(): 업종코드가 존재하지 않을 경우 TYPE_UNLISTED를 반환한다.")
    void 업종_코드가_존재하지_않을_경우_TYPE_UNLISTED를_반환한다(final String invalidBusinessTypeCode) {
        // given
        // when
        final BusinessType actual = BusinessType.from(invalidBusinessTypeCode);

        // then
        assertThat(actual).isEqualTo(TYPE_UNLISTED);
    }
}
