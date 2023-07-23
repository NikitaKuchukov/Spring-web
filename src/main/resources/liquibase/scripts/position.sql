-- liquibase formatted sql


-- changeset test:1
CREATE TABLE position
(
    id       SERIAL PRIMARY KEY,
    position VARCHAR(255)
);