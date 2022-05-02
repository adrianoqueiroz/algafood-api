create sequence forma_pagamento_id_seq start 1 increment 1;
create sequence grupo_id_seq start 1 increment 1;
create sequence permissao_id_seq start 1 increment 1;
create sequence produto_id_seq start 1 increment 1;
create sequence restaurante_id_seq start 1 increment 1;
create table forma_pagamento (
    id int8 not null,
    descricao varchar(255) not null,
    primary key (id));

create table grupo (
    id int8 not null,
    nome varchar(255) not null,
    primary key (id));

create table grupo_has_permissao (
    grupo_id int8 not null,
    permissao_id int8 not null);

create table permissao (
    id int8 not null,
    descricao varchar(255),
    nome varchar(255) not null,
    primary key (id));

create table produto (
    id int8 not null,
    ativo boolean not null,
    descricao varchar(255),
    nome varchar(255) not null,
    preco numeric(19, 2) not null,
    restaurante_id int8,
    primary key (id));

create table restaurante (
    id int8 not null,
    created_at timestamp not null,
    endereco_bairro varchar(255),
    endereco_cep varchar(255),
    endereco_complemento varchar(255),
    endereco_logradouro varchar(255),
    endereco_numero varchar(255),
    nome varchar(255) not null,
    taxa_frete numeric(19, 2) not null,
    updated_at timestamp not null,
    cozinha_id int8 not null,
    endereco_cidade_id int8,
    primary key (id));

create table restaurante_has_forma_pagamento (
    restaurante_id int8 not null,
    forma_pagamento_id int8 not null);

create table usuario (
    id int8 not null,
    created_at timestamp not null,
    email varchar(255) not null,
    nome varchar(255) not null,
    senha varchar(255) not null,
    updated_at timestamp not null,
    primary key (id));

create table usuario_grupo (
    usuario_id int8 not null,
    grupo_id int8 not null);

alter table if exists grupo_has_permissao add constraint fk_permissao_id foreign key (permissao_id) references permissao;
alter table if exists grupo_has_permissao add constraint fk_groupo_id foreign key (grupo_id) references grupo;
alter table if exists produto add constraint fk_restaurante_id foreign key (restaurante_id) references restaurante;
alter table if exists restaurante add constraint fk_cozinha_id foreign key (cozinha_id) references cozinha;
alter table if exists restaurante add constraint fk_endereco_cidade_id foreign key (endereco_cidade_id) references cidade;
alter table if exists restaurante_has_forma_pagamento add constraint fk_forma_pagamento_id foreign key (forma_pagamento_id) references forma_pagamento;
alter table if exists restaurante_has_forma_pagamento add constraint fk_restaurante_id foreign key (restaurante_id) references restaurante;
alter table if exists usuario_grupo add constraint fk_grupo_id foreign key (grupo_id) references grupo;
alter table if exists usuario_grupo add constraint fk_usuario_id foreign key (usuario_id) references usuario;
