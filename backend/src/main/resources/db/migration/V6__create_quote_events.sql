CREATE TABLE quote_events(
	id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
	quote_id UUID NOT NULL REFERENCES quotes(id),
	event_type VARCHAR(50),
	actor VARCHAR(100),
	metadata JSONB,
	created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);