package com.toggn.mma.itp.notice.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ServiceStatusCode {

    CODE002("002", "보충역"),
    CODE006("006", "현역");

    private final String code;
    private final String type;
}
