package com.tonggn.mma.jobposting.command.domain;

import java.util.Arrays;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum AgentType {
  SKILLED_INDUSTRIAL_PERSONNEL("1", "산업기능요원"),
  DETAIL_RESEARCH_PERSONNEL("2", "전문연구요원"),
  RESERVE_FORCES_FOR_SEA_DUTY("3", "승선근무예비역"),
  NONE("0", "미기재");

  private final String code;
  private final String name;

  public static AgentType of(final String code) {
    return Arrays.stream(values())
        .filter(value -> value.code.equals(code))
        .findFirst()
        .orElse(NONE);
  }
}
