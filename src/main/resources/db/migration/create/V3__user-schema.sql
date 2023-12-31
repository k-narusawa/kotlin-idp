CREATE TABLE user
(
    user_id         VARCHAR(255) PRIMARY KEY,
    login_id        VARCHAR(255) NOT NULL,
    password        VARCHAR(255) NOT NULL,
    roles           VARCHAR(255) NOT NULL,
    is_lock         BOOLEAN      NOT NULL,
    failed_attempts INT          NOT NULL,
    lock_time       DATETIME,
    is_disabled     BOOLEAN      NOT NULL,
    created_at      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
