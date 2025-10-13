package com.singularities.api.data.repository;

import com.singularities.api.data.entity.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserModel, UUID> {

    Optional<UserModel> findByEmail(String email);

    long countByLastLoginBetween(LocalDateTime startDate, LocalDateTime endDate);

    long countByCreationDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}
