--liquibase formatted sql
--changeset author:slava dbms:postgresql
CREATE TABLE IF NOT EXISTS users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    first_name VARCHAR(35) NOT NULL,
    last_name VARCHAR(45) NOT NULL,
    username VARCHAR(45) NOT NULL,
    status VARCHAR(255),
    chat_id BIGINT,
    bag_id UUID,
    FOREIGN KEY (bag_id) REFERENCES bags(id)
);
--rollback DROP TABLE users;