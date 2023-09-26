package com.toggn.mma.itp.enterprise.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum EnterpriseScaleCode {

    CODE_NOT_EXISTS("-1", "미기재"),
    CODE_1("1", "대기업"),
    CODE_2("2", "중소기업"),
    CODE_3("3", "벤처기업"),
    CODE_4("4", "중견기업"),
    CODE_A1("A1", "농어민후계 등"),
    CODE_Z("Z", "해당사항없음");

    private final String code;
    private final String type;

    public static EnterpriseScaleCode from(final String code) {
        return Arrays.stream(values())
                .filter(value -> value.code.equals(code))
                .findFirst()
                .orElse(CODE_NOT_EXISTS);
    }
}
