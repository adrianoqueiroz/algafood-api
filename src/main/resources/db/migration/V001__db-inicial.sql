CREATE TABLE cozinha (
    id int8 NOT NULL,
    nome varchar(255) NOT NULL,
    CONSTRAINT cozinha_pkey PRIMARY KEY (id)
);

CREATE SEQUENCE cozinha_id_seq START 1;