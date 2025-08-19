package com.singularities.api.data.constant;

public enum ESettingKey {
    AUTH_AUTHORIZED_DOMAIN(true);

    private final boolean multiple;

    ESettingKey(boolean multiple) {
        this.multiple = multiple;
    }

    public boolean isMultiple() {
        return multiple;
    }
}