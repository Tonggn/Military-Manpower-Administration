package com.toggn.mma.itp.notice.ui;

import com.toggn.mma.itp.enterprise.domain.BusinessType;
import com.toggn.mma.itp.notice.application.NoticeQueryService;
import com.toggn.mma.itp.notice.application.dto.NoticeFilterRequest;
import com.toggn.mma.itp.notice.domain.AgentType;
import com.toggn.mma.itp.notice.domain.ServiceStatusType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/notices")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeQueryService noticeQueryService;

    @GetMapping
    public String notices(
            final Model model,
            @RequestParam(value = "page", defaultValue = "0") final int pageNum,
            final NoticeFilterRequest noticeFilterRequest
    ) {
        model.addAttribute("notices", noticeQueryService.findAllNotices(pageNum, noticeFilterRequest));
        model.addAttribute("noticeFilterRequest", noticeFilterRequest);
        model.addAttribute("serviceTypes", ServiceStatusType.validValues());
        model.addAttribute("agentTypes", AgentType.validValues());
        model.addAttribute("businessTypes", BusinessType.validValues());

        return "notices";
    }
}
