package com.tonggn.mma.company.command.domain;

import java.util.Arrays;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ScaleType {
  // 1, 대기업
  MAJOR_COMPANY("1", "대기업"),
  // 2, 중소기업
  SMALL_AND_MEDIUM_SIZED_COMPANY("2", "중소기업"),
  // 3, 벤처기업
  VENTURE_COMPANY("3", "벤처기업"),
  // 4, 중견기업
  MIDDLE_SIZED_COMPANY("4", "중견기업"),
  // A1, 농어민후계 등
  FARMERS_AND_FISHERMEN("A1", "농어민후계 등"),
  // Z, 해당사항없음
  NONE("Z", "해당사항없음");

  private final String code;
  private final String name;

  public static ScaleType from(final String code) {
    return Arrays.stream(values())
        .filter(scaleType -> scaleType.code.equals(code))
        .findFirst()
        .orElse(NONE);
  }
}
