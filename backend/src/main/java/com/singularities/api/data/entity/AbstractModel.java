package com.singularities.api.data.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AbstractModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID id;

    @CreatedDate
    @Column(name = "creation_date", columnDefinition = "TIMESTAMP", nullable = false, updatable = false)
    private LocalDateTime creationDate;

    @LastModifiedDate
    @Column(name = "last_update", columnDefinition = "TIMESTAMP")
    private LocalDateTime lastUpdate;

}