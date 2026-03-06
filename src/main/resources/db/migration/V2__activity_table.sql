CREATE TABLE activities (
                            id BIGSERIAL PRIMARY KEY,
                            activity_name VARCHAR(255) NOT NULL UNIQUE
);

INSERT INTO activities (activity_name) VALUES
    ('Onboarding');