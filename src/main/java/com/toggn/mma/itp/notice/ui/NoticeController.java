package com.toggn.mma.itp.notice.ui;

import com.toggn.mma.itp.notice.application.NoticeQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/notices")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeQueryService noticeQueryService;

    @GetMapping
    public String notices(final Model model) {
        model.addAttribute("notices", noticeQueryService.findAllNotices());
        return "notices";
    }
}
