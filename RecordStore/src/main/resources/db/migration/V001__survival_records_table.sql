--CREATE TABLE `survival_records` (
--    `game_uuid` char(36) NOT NULL,
--    `nickname` char(36) NOT NULL,
--    `score` bigint NOT NULL,
--    `created_at` datetime NOT NULL,
--    PRIMARY KEY (`game_uuid`),
--    INDEX `score_index` (`score`)
--);

CREATE TABLE IF NOT EXISTS survival_records (
    game_uuid char(36) NOT NULL PRIMARY KEY,
    nickname char(36) NOT NULL,
    score bigint NOT NULL,
    created_at TIMESTAMP NOT NULL
);

CREATE INDEX IF NOT EXISTS survival_score_index ON survival_records (score);