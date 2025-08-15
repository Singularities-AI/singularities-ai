CREATE TABLE settings
(
    id            UUID         NOT NULL PRIMARY KEY DEFAULT uuid_generate_v4(),
    key           VARCHAR(255) NOT NULL UNIQUE,
    is_multiple   BOOLEAN      NOT NULL             DEFAULT false,
    last_update   TIMESTAMP NULL,
    creation_date TIMESTAMP    NOT NULL
);

CREATE TABLE setting_values
(
    id            UUID      NOT NULL PRIMARY KEY DEFAULT uuid_generate_v4(),
    setting_id    UUID      NOT NULL REFERENCES settings (id) ON DELETE CASCADE,
    value         TEXT      NOT NULL,
    last_update   TIMESTAMP NULL,
    creation_date TIMESTAMP NOT NULL
);

ALTER TABLE setting_values
    ADD CONSTRAINT uq_setting_value UNIQUE (setting_id, value);
CREATE INDEX idx_settings_key ON settings (key);
CREATE INDEX idx_settings_value_setting_id ON setting_values (setting_id);