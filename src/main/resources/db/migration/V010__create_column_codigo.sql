ALTER TABLE IF EXISTS pedido
    ADD COLUMN codigo uuid NOT NULL DEFAULT gen_random_uuid();

ALTER TABLE pedido
    add constraint uk_pedido_codigo unique (codigo);