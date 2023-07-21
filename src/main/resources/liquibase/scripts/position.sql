-- liquibase formatted sql


-- changeset test:1
CREATE TABLE position
(
    id       BIGSERIAL PRIMARY KEY,
    position VARCHAR(255)
);