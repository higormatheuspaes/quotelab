CREATE TABLE users (
	id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
	name VARCHAR(150) NOT NULL,
	email VARCHAR(255) NOT NULL UNIQUE,
	password_hash VARCHAR(255) NOT NULL,
	company_name VARCHAR(150),
	document VARCHAR(20),
	phone VARCHAR(20),
	logo_url VARCHAR(500),
	currency VARCHAR(3) DEFAULT 'BRL',
	default_notes TEXT,
	created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
	updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);