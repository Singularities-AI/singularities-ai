CREATE TABLE agentsa
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