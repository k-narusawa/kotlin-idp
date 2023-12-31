CREATE TABLE user_activity
(
    id            INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    user_id       VARCHAR(255) NOT NULL,
    activity_type VARCHAR(255) NOT NULL,
    activity_data JSON                  DEFAULT NULL,
    timestamp     TIMESTAMP             DEFAULT CURRENT_TIMESTAMP,
    created_at    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
