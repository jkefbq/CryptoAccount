--liquibase formatted sql
--changeset author:slava dbms:postgresql
CREATE TABLE IF NOT EXISTS user_questions (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    question TEXT NOT NULL,
    created_at TIMESTAMP,
    replied BOOLEAN DEFAULT false,
    version INT,
    user_id UUID,
    FOREIGN KEY (user_id) REFERENCES users(id)
);
--rollback DROP TABLE users;