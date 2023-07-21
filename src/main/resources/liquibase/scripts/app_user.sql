-- liquibase formatted sql


-- changeset test:1
CREATE TYPE role as enum('ADMIN', 'USER');

-- changeset test:2
CREATE TABLE app_user
(
    id       BIGSERIAL PRIMARY KEY,
    login    VARCHAR(255),
    password TIMESTAMP,
    role     role
);





