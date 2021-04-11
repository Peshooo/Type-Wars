CREATE TABLE IF NOT EXISTS notifications (
    id SERIAL PRIMARY KEY,
    message varchar(1000) NOT NULL,
    created_at TIMESTAMP NOT NULL
);