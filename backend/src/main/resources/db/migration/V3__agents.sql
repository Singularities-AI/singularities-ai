CREATE TABLE agents
(
    id            UUID         NOT NULL PRIMARY KEY DEFAULT uuid_generate_v4(),
    icon          VARCHAR(255) NOT NULL,
    name          VARCHAR(255) NOT NULL,
    description   VARCHAR(255) NOT NULL,
    prompt        TEXT         NOT NULL,
    model_id      UUID         NOT NULL,
    last_update   TIMESTAMP NULL,
    creation_date TIMESTAMP    NOT NULL,
    CONSTRAINT model_id_fk FOREIGN KEY (model_id) REFERENCES models (id)
);

ALTER TABLE auth_tokens
    ADD COLUMN creation_date TIMESTAMP NOT NULL DEFAULT now();
ALTER TABLE auth_tokens
    ADD COLUMN last_update TIMESTAMP NULL;

ALTER TABLE messages
    ADD COLUMN creation_date TIMESTAMP NOT NULL DEFAULT now();
ALTER TABLE messages
    ADD COLUMN last_update TIMESTAMP NULL;

ALTER TABLE models
    ADD COLUMN creation_date TIMESTAMP NOT NULL DEFAULT now();
ALTER TABLE models
    ADD COLUMN last_update TIMESTAMP NULL;

ALTER TABLE profiles
    ADD COLUMN creation_date TIMESTAMP NOT NULL DEFAULT now();
ALTER TABLE profiles
    ADD COLUMN last_update TIMESTAMP NULL;

ALTER TABLE roles
    ADD COLUMN creation_date TIMESTAMP NOT NULL DEFAULT now();
ALTER TABLE roles
    ADD COLUMN last_update TIMESTAMP NULL;