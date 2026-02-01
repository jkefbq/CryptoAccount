--liquibase formatted sql
--changeset author:slava dbms:postgresql
CREATE TABLE IF NOT EXISTS bags (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    created_at DATE NOT NULL,
    totaL_cost NUMERIC(38, 2) NOT NULL,
    asset_count INT NOT NULL,
    chat_id BIGINT,
    version BIGINT
);
--rollback DROP TABLE bags;