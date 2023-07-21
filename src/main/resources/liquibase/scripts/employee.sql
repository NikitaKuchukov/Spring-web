-- liquibase formatted sql


-- changeset test:1
CREATE TABLE employee
(
    id       BIGSERIAL PRIMARY KEY,
    name     VARCHAR(255),
    salary   INT,
    position BIGINT REFERENCES position (id)
);