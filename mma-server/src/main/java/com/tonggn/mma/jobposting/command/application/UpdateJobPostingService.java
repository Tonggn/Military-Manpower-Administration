package com.tonggn.mma.jobposting.command.application;

import com.tonggn.mma.jobposting.command.domain.JobPosting;
import com.tonggn.mma.jobposting.command.domain.JobPostingRepository;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UpdateJobPostingService {

  private final JobPostingRepository jobPostingRepository;

  public void saveOrUpdateJobPostings(final List<JobPostingItem> items) {
    final Map<String, JobPosting> postings = items.stream()
        .collect(Collectors.toMap(JobPostingItem::postingNumber, JobPostingItem::toEntity));

    // update saved postings
    final List<JobPosting> savedPostings = filterSavedPostings(postings.values());
    savedPostings.forEach(posting -> posting.update(postings.get(posting.getPostingNumber())));
    jobPostingRepository.saveAll(savedPostings);

    // save new postings
    final List<String> savedPostingNumbers = extractPostingNumbers(savedPostings);
    final List<JobPosting> newPostings = postings.values().stream()
        .filter(posting -> isNewPosting(posting, savedPostingNumbers))
        .toList();
    jobPostingRepository.saveAll(newPostings);
  }

  private List<JobPosting> filterSavedPostings(final Collection<JobPosting> postings) {
    final List<String> postingNumbers = extractPostingNumbers(postings);
    return jobPostingRepository.findAllByPostingNumberIn(postingNumbers);
  }

  private List<String> extractPostingNumbers(final Collection<JobPosting> postings) {
    return postings.stream()
        .map(JobPosting::getPostingNumber)
        .toList();
  }

  private boolean isNewPosting(final JobPosting posting, final List<String> savedPostingNumbers) {
    return !savedPostingNumbers.contains(posting.getPostingNumber());
  }
}
