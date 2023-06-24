INSERT INTO `user` (`user_id`, `login_id`, `password`, `is_lock`, `failed_attempts`, `lock_time`, `is_disabled`, `created_at`, `updated_at`)
VALUES
	('8ad115ea-20da-4785-b708-587584b9fef5', 'admin', '$2a$10$2LQvgMLq3WZJTQxRIYAanu3V7EMnQzj.mP5KsI8ruSDTeL0c92gta', 0, NULL, NULL, 0, '2023-01-01 00:00:00', '2023-01-01 00:00:00');

INSERT INTO `role` (`user_id`, `role`, `created_at`, `updated_at`)
VALUES
	('8ad115ea-20da-4785-b708-587584b9fef5', 'ADMIN', '2023-01-01 00:00:00', '2023-01-01 00:00:00'),
	('8ad115ea-20da-4785-b708-587584b9fef5', 'USER', '2023-01-01 00:00:00', '2023-01-01 00:00:00');
