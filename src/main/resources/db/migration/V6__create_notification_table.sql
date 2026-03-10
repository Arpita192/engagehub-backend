CREATE TABLE notification (
                              id BIGSERIAL PRIMARY KEY,
                              user_id BIGINT NOT NULL REFERENCES users(id),
                              channel VARCHAR(10) NOT NULL,
                              activity_name VARCHAR(100) NOT NULL,
                              consent_type VARCHAR(20),
                              status VARCHAR(20),
                              mobile VARCHAR(15),
                              created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
