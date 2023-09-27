package com.toggn.mma.itp.notice.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum ServiceStatusCode {

    CODE_NOT_EXISTS("-1", "미기재"),
    CODE002("002", "보충역"),
    CODE006("006", "현역");

    private final String code;
    private final String type;

    public static ServiceStatusCode from(final String code) {
        return Arrays.stream(values())
                .filter(value -> value.code.equals(code))
                .findFirst()
                .orElse(CODE_NOT_EXISTS);
    }
}
