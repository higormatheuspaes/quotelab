CREATE TABLE clients(
	id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
	user_id UUID NOT NULL REFERENCES users(id),
	name VARCHAR(150) NOT NULL,
	email VARCHAR(250) NOT NULL,
	phone VARCHAR(20),
	company VARCHAR(150),
	document VARCHAR(20),
	notes TEXT,
	created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);