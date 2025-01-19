-- Crear la tabla 'topics' para PostgreSQL
CREATE TABLE topics (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    message TEXT NOT NULL,
    author VARCHAR(255) NOT NULL,
    course VARCHAR(255) NOT NULL,
    CONSTRAINT unique_title_message UNIQUE (title, message)
);
