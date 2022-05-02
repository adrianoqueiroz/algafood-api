ALTER TABLE cozinha ALTER id SET DEFAULT NEXTVAL('cozinha_id_seq');

CREATE SEQUENCE estado_id_seq START 1;

CREATE TABLE estado (
   id bigint NOT NULL DEFAULT NEXTVAL('estado_id_seq'),
   nome varchar(80) NOT NULL,
   sigla varchar(2) NOT NULL,
   CONSTRAINT estado_pkey PRIMARY KEY (id)
);

CREATE SEQUENCE cidade_id_seq START 1;

CREATE TABLE cidade (
   id int8 NOT NULL DEFAULT NEXTVAL('cidade_id_seq'),
   nome varchar(255) NOT NULL,
   estado_id int8 NOT NULL,
   CONSTRAINT cidade_pkey PRIMARY KEY (id),
   CONSTRAINT fk_estado FOREIGN KEY (estado_id) REFERENCES estado(id)
);

