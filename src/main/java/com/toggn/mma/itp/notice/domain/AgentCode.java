package com.toggn.mma.itp.notice.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum AgentCode {

    CODE_NOT_EXISTS("-1", "미기재"),
    CODE1("1", "산업기능요원"),
    CODE2("2", "전문연구요원"),
    CODE3("3", "승선근무예비역");

    private final String code;
    private final String type;

    public static AgentCode from(final String code) {
        return Arrays.stream(values())
                .filter(value -> value.code.equals(code))
                .findFirst()
                .orElse(CODE_NOT_EXISTS);
    }
}
