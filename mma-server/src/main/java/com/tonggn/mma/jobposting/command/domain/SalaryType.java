package com.tonggn.mma.jobposting.command.domain;

import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SalaryType {
  CODE_07("07", 2_000, 2_200),
  CODE_08("08", 2_200, 2_400),
  CODE_09("09", 2_400, 2_600),
  CODE_10("10", 2_600, 2_800),
  CODE_11("11", 3_000, 3_200),
  CODE_12("12", 3_200, 3_400),
  CODE_13("13", 3_400, 3_600),
  CODE_14("14", 3_600, 3_800),
  CODE_15("15", 3_800, 4_000),
  CODE_16("16", 4_000, 5_000),
  CODE_17("17", 5_000, 6_000),
  CODE_18("18", 6_000, 7_000),
  CODE_19("19", 7_000, 8_000),
  CODE_20("20", 8_000, 9_000),
  CODE_21("21", 9_000, 10_000),
  CODE_25("25", 0, 2_000),
  CODE_30("30", 2_800, 3_000),
  NONE("00", 0, 0);

  private final String code;
  private final int minSalary;
  private final int maxSalary;

  public static SalaryType of(final String code) {
    return Arrays.stream(values())
        .filter(value -> value.code.equals(code))
        .findFirst()
        .orElse(NONE);
  }
}
