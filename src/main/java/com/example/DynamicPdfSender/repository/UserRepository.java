package com.example.DynamicPdfSender.repository;

import com.example.DynamicPdfSender.model.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserDetail, Long> {
    Optional<UserDetail> findByFinCode(String finCode);
    Optional<UserDetail> findByEmail(String email);
}
