-- liquibase formatted sql


-- changeset test:1
CREATE TABLE report
(
    id         SERIAL PRIMARY KEY,
    report     VARCHAR(255),
    created_at TIMESTAMP,
    path       VARCHAR(255)
);