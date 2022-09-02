ALTER TABLE IF EXISTS pedido
    RENAME createdat TO created_at;

ALTER TABLE IF EXISTS pedido
    RENAME updatedat TO updated_at;

ALTER TABLE IF EXISTS pedido
    RENAME confirmedat TO confirmed_at;

ALTER TABLE IF EXISTS pedido
    RENAME cancelledat TO cancelled_at;

ALTER TABLE IF EXISTS pedido
    RENAME deliveredat TO delivered_at;