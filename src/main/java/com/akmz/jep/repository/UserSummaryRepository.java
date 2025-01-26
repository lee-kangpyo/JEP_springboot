package com.akmz.jep.repository;

import com.akmz.jep.domain.JepmSummary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSummaryRepository extends JpaRepository<JepmSummary, Integer> {
//    Optional<JepmSummary> findBy(Integer integer);
}
