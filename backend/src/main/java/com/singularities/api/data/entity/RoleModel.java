package com.singularities.api.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "roles")
public class RoleModel extends AbstractModel {

    @Column(length = 20)
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Set<UserModel> users = new HashSet<>();
}