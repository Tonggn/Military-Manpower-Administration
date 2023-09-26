package com.toggn.mma.itp.enterprise.domain.repository;

import com.toggn.mma.itp.enterprise.domain.Enterprise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnterpriseRepository extends JpaRepository<Enterprise, Long> {
}
