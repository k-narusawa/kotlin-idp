CREATE TABLE user_mfa
(
    user_id         VARCHAR(255) PRIMARY KEY,
    type            VARCHAR(255) NOT NULL,
    secret_key      VARCHAR(255),
    validation_code VARCHAR(255),
    scratch_codes   VARCHAR(255),
    created_at      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
