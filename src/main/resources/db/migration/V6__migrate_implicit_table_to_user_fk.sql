-- 1. Add new column
ALTER TABLE implicit ADD COLUMN user_id BIGINT;

-- 2. Copy data from consent table
UPDATE implicit i
SET user_id = c.user_id
    FROM user_consents c
WHERE i.consent_id = c.id;

-- 3. Add foreign key
ALTER TABLE implicit
    ADD CONSTRAINT fk_implicit_user
        FOREIGN KEY (user_id)
            REFERENCES users(id)
            ON DELETE CASCADE;

-- 4. Add unique constraint
ALTER TABLE implicit
    ADD CONSTRAINT uk_implicit_user_activity
        UNIQUE (user_id, activity_id);

-- 5. Drop old foreign key
ALTER TABLE implicit
DROP CONSTRAINT fk_implicit_consent;

-- 6. Drop old column
ALTER TABLE implicit
DROP COLUMN consent_id;