-- liquibase formatted sql


-- changeset test:1
CREATE TABLE employee
(
    id       SERIAL PRIMARY KEY,
    name     VARCHAR(255),
    salary   INT,
    position_id INT REFERENCES position (id)
);