-- liquibase formatted sql


-- changeset test:1
CREATE TABLE app_user
(
    id       SERIAL PRIMARY KEY,
    login    VARCHAR(255),
    password VARCHAR(255),
    role     VARCHAR(255)
);