package com.toggn.mma.enterprise.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EnterpriseScaleCode {

    CODE1("1", "대기업"),
    CODE2("2", "중소기업"),
    CODE3("3", "벤처기업"),
    CODE4("4", "중견기업"),
    CODEA1("A1", "농어민후계 등"),
    CODEZ("Z", "해당사항없음");

    private final String code;
    private final String type;
}
