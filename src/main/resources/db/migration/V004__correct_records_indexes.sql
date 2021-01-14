DROP INDEX IF EXISTS standard_score_index;
DROP INDEX IF EXISTS survival_score_index;

CREATE INDEX IF NOT EXISTS standard_created_at_score_index ON standard_records (created_at, score);
CREATE INDEX IF NOT EXISTS survival_created_at_score_index ON survival_records (created_at, score);