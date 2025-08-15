package com.singularities.api.data.repository;

import com.singularities.api.data.entity.UserModel;
import com.singularities.api.data.entity.UserRoleModel;
import com.singularities.api.data.entity.UserRoleModelCompositeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface UserRoleRepository extends JpaRepository<UserRoleModel, UserRoleModelCompositeId> {

    @Query("select u from UserRoleModel u where u.userRoleModelCompositeId.user.id = ?1")
    List<UserRoleModel> findAllByUserId(UUID userId);

    @Modifying
    @Query("DELETE FROM UserRoleModel u WHERE u.userRoleModelCompositeId.user = :user")
    void deleteAllByUser(@Param("user") UserModel user);
}
