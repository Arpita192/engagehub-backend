ALTER TABLE notification DROP COLUMN activity_name;

ALTER TABLE notification ADD COLUMN activity_id BIGINT;