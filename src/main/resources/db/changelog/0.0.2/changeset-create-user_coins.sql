--liquibase formatted sql
--changeset author:slava dbms:postgresql
CREATE TABLE IF NOT EXISTS user_coins (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    coin VARCHAR(255),
    count NUMERIC(255),
    chat_id BIGINT,
    asset_do VARCHAR(255)
);
--rollback DROP TABLE users;