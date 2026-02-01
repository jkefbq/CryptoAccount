--liquibase formatted sql
--changeset author:slava dbms:postgresql
CREATE TABLE IF NOT EXISTS assets (
    coin_name VARCHAR(255) PRIMARY KEY,
    coin_count NUMERIC(38, 2),
    bag_id UUID,
    FOREIGN KEY (bag_id) REFERENCES bags(id)
);
--rollback DROP TABLE users;