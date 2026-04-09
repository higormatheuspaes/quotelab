CREATE TABLE quote_versions(
	id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
	quote_id UUID NOT NULL REFERENCES quotes(id),
	version_number INTEGER NOT NULL,
	snapshot JSONB NOT NULL,
	created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);