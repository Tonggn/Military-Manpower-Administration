package com.toggn.mma.enterprise.domain.repository;

import com.toggn.mma.enterprise.domain.Enterprise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnterpriseRepository extends JpaRepository<Enterprise, Long> {
}
