-- Agregar las nuevas columnas con valores por defecto
ALTER TABLE topics
ADD COLUMN creation_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
ADD COLUMN status VARCHAR(50) NOT NULL DEFAULT 'active';  -- Valor por defecto

-- Crear índice en la columna creation_date para mejorar las consultas por fecha
CREATE INDEX IF NOT EXISTS idx_creation_date ON topics(creation_date);
