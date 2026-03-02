CREATE TABLE users (
                       id BIGSERIAL PRIMARY KEY,
                       name VARCHAR(100),
                       mobile_number VARCHAR(15),
                       email VARCHAR(100) UNIQUE NOT NULL,
                       password VARCHAR(255),
                       role VARCHAR(20),
                       status VARCHAR(20),
                       created_at TIMESTAMP
);

-- Index for faster login/email search
CREATE INDEX idx_users_email ON users(email);