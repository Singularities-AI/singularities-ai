package com.singularities.api.data.entity;

import com.singularities.api.data.constant.ESettingKey;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "settings", uniqueConstraints = {@UniqueConstraint(columnNames = "key")})
public class SettingModel extends AbstractModel {

    @Enumerated(EnumType.STRING)
    @Column(name = "key", nullable = false)
    private ESettingKey key;

    @Column(name = "is_multiple", nullable = false)
    private boolean isMultiple;

    @OneToMany(mappedBy = "setting", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SettingValueModel> values = new ArrayList<>();
}