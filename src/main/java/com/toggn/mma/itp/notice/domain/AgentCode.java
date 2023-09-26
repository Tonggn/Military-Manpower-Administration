package com.toggn.mma.itp.notice.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AgentCode {

    CODE1("1", "산업기능요원"),
    CODE2("2", "전문연구요원"),
    CODE3("3", "승선근무예비역");

    private final String code;
    private final String type;
}
