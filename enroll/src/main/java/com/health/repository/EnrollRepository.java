package com.health.repository;

import javax.transaction.Transactional;
import com.health.jpa.Enroll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface EnrollRepository extends JpaRepository<Enroll, Long> {

     Enroll saveEnroll(Enroll enroll);

     int deleteEnrollById(Long id);

}
