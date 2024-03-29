CREATE SEQUENCE pedido_id_seq START 1;

create table pedido (
    id bigint NOT NULL DEFAULT NEXTVAL('pedido_id_seq'),
    subtotal decimal(10,2) not null,
    taxa_frete decimal(10,2) not null,
    valor_total decimal(10,2) not null,

    restaurante_id bigint not null,
    usuario_cliente_id bigint not null,
    forma_pagamento_id bigint not null,

    endereco_cidade_id bigint not null,
    endereco_cep varchar(9) not null,
    endereco_logradouro varchar(100) not null,
    endereco_numero varchar(20) not null,
    endereco_complemento varchar(60) null,
    endereco_bairro varchar(60) not null,

    status varchar(10) not null,
    createdAt timestamp not null,
    updatedAt timestamp not null,
    confirmedAt timestamp null,
    cancelledAt timestamp null,
    deliveredAt timestamp null,

    primary key (id),

    constraint fk_pedido_endereco_cidade foreign key (endereco_cidade_id) references cidade (id),
    constraint fk_pedido_restaurante foreign key (restaurante_id) references restaurante (id),
    constraint fk_pedido_usuario_cliente foreign key (usuario_cliente_id) references usuario (id),
    constraint fk_pedido_forma_pagamento foreign key (forma_pagamento_id) references forma_pagamento (id)
);

CREATE SEQUENCE item_pedido_id_seq START 1;

create table item_pedido (
    id bigint NOT NULL DEFAULT NEXTVAL('item_pedido_id_seq'),
    quantidade smallint not null,
    preco_unitario decimal(10,2) not null,
    preco_total decimal(10,2) not null,
    observacao varchar(255) null,
    pedido_id bigint not null,
    produto_id bigint not null,

    primary key (id),
    unique (pedido_id, produto_id),
    constraint fk_item_pedido_pedido foreign key (pedido_id) references pedido (id),
    constraint fk_item_pedido_produto foreign key (produto_id) references produto (id)
);