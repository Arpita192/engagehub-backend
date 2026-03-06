ALTER TABLE implicit
DROP CONSTRAINT IF EXISTS fk_implicit_consent;

ALTER TABLE implicit
DROP COLUMN IF EXISTS consent_id;