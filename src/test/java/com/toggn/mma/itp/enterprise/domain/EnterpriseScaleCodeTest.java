package com.toggn.mma.itp.enterprise.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;

import static com.toggn.mma.itp.enterprise.domain.EnterpriseScaleCode.CODE_NOT_EXISTS;
import static org.assertj.core.api.Assertions.assertThat;

class EnterpriseScaleCodeTest {

    @ParameterizedTest
    @EmptySource
    @NullSource
    @DisplayName("from(): 기업 규모 코드가 존재하지 않을 경우 CODE_NOT_EXISTS를 반환한다.")
    void 기업_규모_코드가_존재하지_않을_경우_CODE_NOT_EXISTS를_반환한다(final String notExistsCode) {
        // given
        // when
        final EnterpriseScaleCode actual = EnterpriseScaleCode.from(notExistsCode);

        // then
        assertThat(actual).isEqualTo(CODE_NOT_EXISTS);
    }
}
