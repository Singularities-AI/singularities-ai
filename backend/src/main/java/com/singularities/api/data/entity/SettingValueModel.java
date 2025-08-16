package com.singularities.api.data.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "setting_values")
public class SettingValueModel extends AbstractModel {

    @ManyToOne
    @JoinColumn(name = "setting_id", nullable = false)
    private SettingModel setting;

    @Column(name = "value", nullable = false)
    private String value;
}