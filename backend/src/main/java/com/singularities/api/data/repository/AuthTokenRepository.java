package com.singularities.api.data.repository;

import com.singularities.api.data.entity.AuthTokenModel;
import com.singularities.api.data.entity.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AuthTokenRepository extends JpaRepository<AuthTokenModel, UUID> {
    void deleteAllByUser(UserModel user);

    Optional<AuthTokenModel> findByUser_EmailAndSecurityCode(String email, String securityCode);
}
