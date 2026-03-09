CREATE TABLE implicit (

                          id BIGSERIAL PRIMARY KEY,

                          user_id BIGINT NOT NULL,

                          activity_id BIGINT NOT NULL,

                          implicit_consent TIMESTAMP NOT NULL,

                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

                          updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

                          CONSTRAINT fk_implicit_user
                              FOREIGN KEY (user_id)
                                  REFERENCES users(id)
                                  ON DELETE CASCADE,

                          CONSTRAINT fk_implicit_activity
                              FOREIGN KEY (activity_id)
                                  REFERENCES activities(id),

                          CONSTRAINT uk_implicit_user_activity
                              UNIQUE (user_id, activity_id)
);