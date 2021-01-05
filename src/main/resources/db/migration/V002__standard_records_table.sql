CREATE TABLE IF NOT EXISTS standard_records (
    game_uuid char(36) NOT NULL PRIMARY KEY,
    nickname char(36) NOT NULL,
    score bigint NOT NULL,
    created_at TIMESTAMP NOT NULL
);

CREATE INDEX IF NOT EXISTS standard_score_index ON standard_records (score);