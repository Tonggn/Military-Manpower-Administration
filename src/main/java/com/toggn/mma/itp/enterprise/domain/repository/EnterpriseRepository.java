package com.toggn.mma.itp.enterprise.domain.repository;

import com.toggn.mma.itp.enterprise.domain.Enterprise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EnterpriseRepository extends JpaRepository<Enterprise, Long> {

    @Query("select e from Enterprise e where e.id in :enterpriseIds")
    List<Enterprise> findAllByIdIn(List<Long> enterpriseIds);

    @Query("select e.name from Enterprise e")
    List<String> findAllNames();

    @Query("select e from Enterprise e where e.name in :names")
    List<Enterprise> findAllByNameIn(List<String> names);
}
