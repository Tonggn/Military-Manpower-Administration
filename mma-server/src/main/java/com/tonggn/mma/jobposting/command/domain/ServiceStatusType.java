package com.tonggn.mma.jobposting.command.domain;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ServiceStatusType {
  REPLACEMENT_STATUS("002", "보충역"),
  ACTIVE_DUTY_STATUS("006", "현역"),
  ETC("009", "기타"),
  NONE("000", "미기재");

  private final String code;
  private final String name;
}
