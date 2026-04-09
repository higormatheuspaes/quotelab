CREATE TABLE quote_items(
	id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
	quote_id UUID NOT NULL REFERENCES quotes(id) ON DELETE CASCADE,
	description VARCHAR(300) NOT NULL,
	quantity NUMERIC(10,2) NOT NULL,
	unit_price NUMERIC(12,2) NOT NULL,
	total NUMERIC(12,2) NOT NULL,
	sort_order INTEGER NOT NULL DEFAULT 0
);