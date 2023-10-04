package com.toggn.mma.itp.notice.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;

@Getter
@RequiredArgsConstructor
public enum AgentType {

    TYPE_UNLISTED("-1", "미기재"),
    TYPE_1("1", "산업기능요원"),
    TYPE_2("2", "전문연구요원"),
    TYPE_3("3", "승선근무예비역");

    private final String code;
    private final String name;

    public static AgentType from(final String code) {
        return Arrays.stream(values())
                .filter(value -> value.code.equals(code))
                .findFirst()
                .orElse(TYPE_UNLISTED);
    }

    public static List<AgentType> validValues() {
        return Arrays.stream(values())
                .filter(value -> !value.equals(TYPE_UNLISTED))
                .toList();
    }

    public boolean isValid() {
        return !this.equals(TYPE_UNLISTED);
    }
}
