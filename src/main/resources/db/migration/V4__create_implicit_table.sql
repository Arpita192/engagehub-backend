CREATE TABLE implicit (
                          id BIGSERIAL PRIMARY KEY,

                          consent_id BIGINT NOT NULL,
                          activity_id BIGINT NOT NULL,

                          implicit_consent TIMESTAMP NOT NULL,

                          created_at TIMESTAMP NULL,
                          updated_at TIMESTAMP NULL,

                          CONSTRAINT fk_implicit_consent
                              FOREIGN KEY (consent_id)
                                  REFERENCES user_consents(id)
                                  ON DELETE CASCADE,

                          CONSTRAINT fk_implicit_activity
                              FOREIGN KEY (activity_id)
                                  REFERENCES activities(id),

                          CONSTRAINT uk_implicit_consent UNIQUE (consent_id)
);