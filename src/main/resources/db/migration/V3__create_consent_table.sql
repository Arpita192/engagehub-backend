CREATE TABLE user_consents (

                               id BIGSERIAL PRIMARY KEY,

                               user_id BIGINT NOT NULL UNIQUE,

                               explicit_consent TIMESTAMP NULL,

                               promotion_consent VARCHAR(3) NOT NULL,

                               created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

                               updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

                               CONSTRAINT fk_user_consents_user
                                   FOREIGN KEY (user_id)
                                       REFERENCES users(id)
                                       ON DELETE CASCADE
);