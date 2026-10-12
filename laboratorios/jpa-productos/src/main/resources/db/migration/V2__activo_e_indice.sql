ALTER TABLE producto ADD COLUMN activo BOOLEAN NOT NULL DEFAULT TRUE;
CREATE INDEX producto_categoria_idx ON producto(categoria_id);
