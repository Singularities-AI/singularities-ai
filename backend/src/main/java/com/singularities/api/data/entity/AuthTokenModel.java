package com.singularities.api.data.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "auth_tokens", uniqueConstraints = {@UniqueConstraint(columnNames = "user_id")})
public class AuthTokenModel extends AbstractModel {

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private UserModel user;

    @Column(name = "expiry_date", nullable = false)
    private LocalDateTime expiryDate;

    @Column(name = "security_code", nullable = false)
    private String securityCode;

    @Column(name = "jwt", nullable = false)
    private String jwt;
}