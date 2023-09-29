package com.toggn.mma.itp.notice.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SalaryCodeTest {

    @ParameterizedTest
    @EmptySource
    @NullSource
    @DisplayName("from(): 급여코드가 존재하지 않을 경우 CODE_NOT_EXISTS를 반환한다.")
    void 급여_코드가_존재하지_않을_경우_CODE_NOT_EXISTS를_반환한다(final String code) {
        // given
        // when
        final SalaryCode actual = SalaryCode.from(code);

        // then
        assertEquals(SalaryCode.CODE_NOT_EXISTS, actual);
    }
}