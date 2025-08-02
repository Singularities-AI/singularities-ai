CREATE
EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE users
(
    id            UUID         NOT NULL PRIMARY KEY DEFAULT uuid_generate_v4(),
    email         VARCHAR(255) NOT NULL UNIQUE,
    creation_date TIMESTAMP    NOT NULL,
    context       TEXT NULL,
    last_update   TIMESTAMP NULL,
    last_login    TIMESTAMP NULL
);

CREATE TABLE roles
(
    id   UUID    NOT NULL PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR NOT NULL
);

CREATE TABLE users_roles
(
    user_id UUID NOT NULL,
    role_id UUID NOT NULL,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT user_id_fk FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT role_id_fk FOREIGN KEY (role_id) REFERENCES roles (id)
);

INSERT INTO roles (name)
VALUES ('ROLE_USER'),
       ('ROLE_MODERATOR'),
       ('ROLE_ADMIN');

CREATE TABLE auth_tokens
(
    id            UUID         NOT NULL PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id       UUID         NOT NULL UNIQUE,
    expiry_date   TIMESTAMP    NOT NULL,
    security_code VARCHAR(255) NOT NULL,
    jwt           VARCHAR(255) NOT NULL,
    CONSTRAINT user_id_fk FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE profiles
(
    id   UUID    NOT NULL PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR NOT NULL
);

CREATE TABLE users_profiles
(
    user_id    UUID NOT NULL,
    profile_id UUID NOT NULL,
    PRIMARY KEY (user_id, profile_id),
    CONSTRAINT user_id_fk FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT profile_id_fk FOREIGN KEY (profile_id) REFERENCES profiles (id)
);

CREATE TABLE models
(
    id             UUID    NOT NULL PRIMARY KEY DEFAULT uuid_generate_v4(),
    name           VARCHAR NOT NULL,
    code           VARCHAR NOT NULL,
    is_default     BOOLEAN NOT NULL             DEFAULT FALSE,
    is_download    BOOLEAN NOT NULL             DEFAULT FALSE,
    is_downloading BOOLEAN NOT NULL             DEFAULT FALSE,
    description    TEXT NULL
);

CREATE TABLE chats
(
    id            UUID      NOT NULL PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id       UUID      NOT NULL,
    model_id      UUID      NOT NULL,
    creation_date TIMESTAMP NOT NULL,
    title         VARCHAR   NOT NULL,
    context       TEXT NULL,
    last_update   TIMESTAMP NULL,
    CONSTRAINT user_id_fk FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT model_id_fk FOREIGN KEY (model_id) REFERENCES models (id)
);

CREATE TABLE messages
(
    id         UUID      NOT NULL PRIMARY KEY DEFAULT uuid_generate_v4(),
    chat_id    UUID      NOT NULL,
    model_id   UUID      NOT NULL,
    role       VARCHAR   NOT NULL, -- 'user' ou 'agent'
    context    TEXT,
    content    TEXT      NOT NULL,
    created_at TIMESTAMP NOT NULL             DEFAULT NOW(),
    CONSTRAINT chat_id_fk FOREIGN KEY (chat_id) REFERENCES chats (id),
    CONSTRAINT model_id_fk FOREIGN KEY (model_id) REFERENCES models (id)
);
