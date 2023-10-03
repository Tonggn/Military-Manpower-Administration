package com.toggn.mma.itp.notice.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum SalaryType {

    TYPE_UNLISTED("-1", 0, 0),
    TYPE_07("07", 20_000_000, 22_000_000),
    TYPE_08("08", 22_000_000, 24_000_000),
    TYPE_09("09", 24_000_000, 26_000_000),
    TYPE_10("10", 26_000_000, 28_000_000),
    TYPE_11("11", 30_000_000, 32_000_000),
    TYPE_12("12", 32_000_000, 34_000_000),
    TYPE_13("13", 34_000_000, 36_000_000),
    TYPE_14("14", 36_000_000, 38_000_000),
    TYPE_15("15", 38_000_000, 40_000_000),
    TYPE_16("16", 40_000_000, 50_000_000),
    TYPE_17("17", 50_000_000, 60_000_000),
    TYPE_18("18", 60_000_000, 70_000_000),
    TYPE_19("19", 70_000_000, 80_000_000),
    TYPE_20("20", 80_000_000, 90_000_000),
    TYPE_21("21", 90_000_000, 100_000_000),
    TYPE_25("25", 0, 20_000_000),
    TYPE_30("30", 28_000_000, 30_000_000);

    private final String code;
    private final int minimumSalary;
    private final int maximumSalary;

    public static SalaryType from(final String code) {
        return Arrays.stream(values())
                .filter(value -> value.code.equals(code))
                .findFirst()
                .orElse(TYPE_UNLISTED);
    }
}
