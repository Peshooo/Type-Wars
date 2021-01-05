CREATE TABLE IF NOT EXISTS announcements (
    title varchar(40) NOT NULL PRIMARY KEY,
    content varchar(300) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    active BOOLEAN NOT NULL
);