package com.akmz.jep.repository;

import com.akmz.jep.domain.JepmUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<JepmUser, Integer> {
    Optional<JepmUser> findByUserId(String userId);
}
