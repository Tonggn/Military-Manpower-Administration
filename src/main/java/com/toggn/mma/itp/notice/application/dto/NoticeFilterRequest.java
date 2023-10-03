package com.toggn.mma.itp.notice.application.dto;

import org.springframework.web.bind.annotation.RequestParam;

public record NoticeFilterRequest(
        @RequestParam(value = "keyword", defaultValue = "") String keyword
) {

    public boolean isValidKeyword() {
        return keyword != null && !keyword.isBlank();
    }
}
