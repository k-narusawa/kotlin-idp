CREATE TABLE user_withdraw
(
    id                INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    user_id           VARCHAR(255) NOT NULL,
    original_login_id VARCHAR(255) NOT NULL,
    withdrawn_at      DATETIME     NOT NULL,
    created_at        DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at        DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
