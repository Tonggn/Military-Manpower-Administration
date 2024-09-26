package com.tonggn.mma.jobposting.command.domain;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobPostingRepository extends JpaRepository<JobPosting, Long> {

  List<JobPosting> findAllByPostingNumberIn(List<String> postingNumbers);
}
