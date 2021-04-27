CREATE TABLE IF NOT EXISTS daily_counters (
    key varchar (20) NOT NULL,
    count bigint NOT NULL,
    day bigint NOT NULL,
    PRIMARY KEY (key, day)
);