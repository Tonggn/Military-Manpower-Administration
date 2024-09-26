package com.tonggn.mma.jobposting.command.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.tonggn.mma.config.DataConfig;
import com.tonggn.mma.jobposting.command.domain.JobPosting;
import com.tonggn.mma.jobposting.command.domain.JobPostingRepository;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@Import(DataConfig.class)
@ActiveProfiles("integration")
@AutoConfigureTestDatabase(replace = Replace.NONE)
class UpdateJobPostingServiceTest {

  @Autowired
  private JobPostingRepository jobPostingRepository;
  @Autowired
  private UpdateJobPostingService updateJobPostingService;

  @BeforeEach
  void setUp() {
    jobPostingRepository.deleteAll();
  }

  @Test
  @DisplayName("새로운 채용공고를 저장한다.")
  void saveNewJobPostings() {
    // given
    final List<JobPostingItem> items = IntStream.range(0, 100)
        .mapToObj(i -> new JobPostingItemBuilder().build())
        .toList();

    final Map<String, JobPosting> expectPostings = items.stream()
        .collect(Collectors.toMap(JobPostingItem::postingNumber, JobPostingItem::toEntity));

    // when
    updateJobPostingService.saveOrUpdateJobPostings(items);
    final List<JobPosting> actualPostings = jobPostingRepository.findAll();

    // then
    for (final JobPosting actual : actualPostings) {
      final JobPosting expect = expectPostings.get(actual.getPostingNumber());
      assertThat(actual).usingRecursiveComparison()
          .ignoringFields("id", "createdAt", "updatedAt")
          .isEqualTo(expect);
    }
  }

  @Test
  @DisplayName("채용공고가 이미 저장된 경우 업데이트한다.")
  void updateSavedJobPostings() {
    // given
    final List<JobPosting> savedPostings = IntStream.range(0, 100)
        .mapToObj(i -> new JobPostingItemBuilder().build().toEntity())
        .toList();
    jobPostingRepository.saveAll(savedPostings);

    final List<JobPostingItem> expectItems = savedPostings.stream()
        .map(posting -> new JobPostingItemBuilder()
            .postingNumber(posting.getPostingNumber())
            .build())
        .toList();

    final Map<String, JobPosting> expectPostings = expectItems.stream()
        .collect(Collectors.toMap(JobPostingItem::postingNumber, JobPostingItem::toEntity));

    // when
    updateJobPostingService.saveOrUpdateJobPostings(expectItems);
    final List<JobPosting> actualPostings = jobPostingRepository.findAll();

    // then
    for (final JobPosting actual : actualPostings) {
      final JobPosting expect = expectPostings.get(actual.getPostingNumber());
      assertThat(actual).usingRecursiveComparison()
          .ignoringFields("id", "createdAt", "updatedAt")
          .isEqualTo(expect);
    }
  }
}
