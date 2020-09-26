package com.health.repository;

import com.health.jpa.Dependent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DependentRepository extends JpaRepository<Dependent, Long> {

        int deleteByIdAndEnrollId(Long id, Long dependentId);

}
