CREATE TABLE user_consents (
                               id BIGSERIAL PRIMARY KEY,

                               user_id BIGINT NOT NULL,

                               explicit_consent TIMESTAMP NULL,

                               promotion_consent VARCHAR(3) NOT NULL,

                               created_at TIMESTAMP NULL,
                               updated_at TIMESTAMP NULL,

                               CONSTRAINT fk_user_consents_user
                                   FOREIGN KEY (user_id)
                                       REFERENCES users(id)
                                       ON DELETE CASCADE,

                               CONSTRAINT uk_user_consents_user
                                   UNIQUE (user_id)
);
