package com.toggn.mma.itp.notice.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum ServiceStatusType {

    TYPE_UNLISTED("-1", "미기재"),
    TYPE_002("002", "보충역"),
    TYPE_006("006", "현역");

    private final String code;
    private final String name;

    public static ServiceStatusType from(final String code) {
        return Arrays.stream(values())
                .filter(value -> value.code.equals(code))
                .findFirst()
                .orElse(TYPE_UNLISTED);
    }
}
