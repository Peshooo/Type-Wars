CREATE TABLE `standard_records` (
    `game_uuid` char(36) NOT NULL,
    `nickname` char(36) NOT NULL,
    `score` bigint NOT NULL,
    `created_at` datetime NOT NULL,
    PRIMARY KEY (`game_uuid`),
    INDEX `score_index` (`score`)
);