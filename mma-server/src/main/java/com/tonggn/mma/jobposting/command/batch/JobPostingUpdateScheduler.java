package com.tonggn.mma.jobposting.command.batch;

import com.tonggn.mma.jobposting.command.application.JobPostingApiService;
import com.tonggn.mma.jobposting.command.application.JobPostingItem;
import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JobPostingUpdateScheduler {

  private final JobPostingApiService jobPostingApiService;

  @PostConstruct
  @Scheduled(cron = "0 0 * * * *") // 초 분 시 일 월 요일
  public void update() {
    try {
      final List<JobPostingItem> items = jobPostingApiService.fetchItems();
    } catch (final IOException e) {
      log.error("Failed to request JobPosting API", e);
    }
  }
}
