CREATE TYPE quote_status AS ENUM (
	'DRAFT',
	'SENT',
	'UNDER_REVIEW',
	'APPROVED',
	'REJECTED',
	'EXPIRED'
);


CREATE TABLE quotes(
	id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
	user_id UUID NOT NULL REFERENCES users(id),
	client_id UUID NOT NULL REFERENCES clients(id),
	public_token UUID NOT NULL UNIQUE,
	title VARCHAR(200) NOT NULL,
	description TEXT,
	status quote_status NOT NULL DEFAULT 'DRAFT',
	version INTEGER NOT NULL DEFAULT 1,
	subtotal NUMERIC(12,2) NOT NULL,
	discount_type VARCHAR(10),
	discount_value NUMERIC(12,2),
	total NUMERIC(12,2),
	valid_until DATE NOT NULL,
	notes TEXT,
	pdf_url VARCHAR(500),
	signed_by VARCHAR(150),
	signed_at TIMESTAMPTZ,
	signer_ip VARCHAR(45),
	created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
	updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);