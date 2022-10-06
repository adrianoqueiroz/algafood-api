lock table grupo_has_permissao, usuario_grupo, restaurante_usuario_responsavel, item_pedido, pedido, usuario, foto_produto, produto, restaurante_has_forma_pagamento, restaurante, forma_pagamento, cidade, estado, cozinha, permissao, grupo;

delete from grupo_has_permissao;
delete from usuario_grupo;
delete from restaurante_usuario_responsavel;
delete from item_pedido;

delete from pedido;
delete from usuario;
delete from foto_produto;
delete from produto;
delete from restaurante_has_forma_pagamento;
delete from restaurante;
delete from forma_pagamento;
delete from cidade;
delete from estado;
delete from cozinha;
delete from permissao;
delete from grupo;

ALTER SEQUENCE produto_id_seq RESTART WITH 1;
ALTER SEQUENCE restaurante_id_seq RESTART WITH 1;
ALTER SEQUENCE cidade_id_seq RESTART WITH 6;
ALTER SEQUENCE estado_id_seq RESTART WITH 4;
ALTER SEQUENCE cozinha_id_seq RESTART WITH 6;
ALTER SEQUENCE permissao_id_seq RESTART WITH 3;
ALTER SEQUENCE forma_pagamento_id_seq RESTART WITH 1;
ALTER SEQUENCE grupo_id_seq RESTART WITH 1;
ALTER SEQUENCE usuario_id_seq RESTART WITH 1;

insert into cozinha (id, nome) values (1, 'Tailandesa');
insert into cozinha (id, nome) values (2, 'Indiana');
insert into cozinha (id, nome) values (3, 'Argentina');
insert into cozinha (id, nome) values (4, 'Brasileira');

insert into estado (id, nome, sigla) values (1, 'Minas Gerais', 'MG');
insert into estado (id, nome, sigla) values (2, 'São Paulo', 'SP');
insert into estado (id, nome, sigla) values (3, 'Ceará', 'CE');

insert into cidade (id, nome, estado_id) values (1, 'Uberlândia', 1);
insert into cidade (id, nome, estado_id) values (2, 'Belo Horizonte', 1);
insert into cidade (id, nome, estado_id) values (3, 'São Paulo', 2);
insert into cidade (id, nome, estado_id) values (4, 'Campinas', 2);
insert into cidade (id, nome, estado_id) values (5, 'Fortaleza', 3);

insert into restaurante (id, nome, taxa_frete, cozinha_id, created_at, updated_at, endereco_cidade_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_bairro, active, aberto) values (nextval('restaurante_id_seq'), 'Thai Gourmet', 10, 1, current_timestamp, current_timestamp, 1, '38400-999', 'Rua João Pinheiro', '1000', 'Centro', true, true);
insert into restaurante (id, nome, taxa_frete, cozinha_id, created_at, updated_at, active, aberto) values (nextval('restaurante_id_seq'), 'Thai Delivery', 9.50, 1, current_timestamp, current_timestamp, true, true);
insert into restaurante (id, nome, taxa_frete, cozinha_id, created_at, updated_at, active, aberto) values (nextval('restaurante_id_seq'), 'Tuk Tuk Comida Indiana', 15, 2, current_timestamp, current_timestamp, true, true);
insert into restaurante (id, nome, taxa_frete, cozinha_id, created_at, updated_at, active, aberto) values (nextval('restaurante_id_seq'), 'Java Steakhouse', 12, 3, current_timestamp, current_timestamp, true, true);
insert into restaurante (id, nome, taxa_frete, cozinha_id, created_at, updated_at, active, aberto) values (nextval('restaurante_id_seq'), 'Lanchonete do Tio Sam', 11, 4, current_timestamp, current_timestamp, true, true);
insert into restaurante (id, nome, taxa_frete, cozinha_id,  created_at, updated_at, active, aberto) values (nextval('restaurante_id_seq'), 'Bar da Maria', 6, 4, current_timestamp, current_timestamp, true, true);

insert into forma_pagamento (id, descricao, updated_at) values (nextval('forma_pagamento_id_seq'), 'Cartão de crédito', current_timestamp);
insert into forma_pagamento (id, descricao, updated_at) values (nextval('forma_pagamento_id_seq'), 'Cartão de débito', current_timestamp);
insert into forma_pagamento (id, descricao, updated_at) values (nextval('forma_pagamento_id_seq'), 'Dinheiro', current_timestamp);

insert into restaurante_has_forma_pagamento (restaurante_id, forma_pagamento_id) values (1, 1), (1, 2), (1, 3), (2, 3), (3, 2), (3, 3), (4, 1), (4, 2), (5, 1), (5, 2), (6, 3);

insert into produto (id, nome, descricao, preco, ativo, restaurante_id) values (nextval('produto_id_seq'), 'Porco com molho agridoce', 'Deliciosa carne suína ao molho especial', 78.90, false, 1);
insert into produto (id, nome, descricao, preco, ativo, restaurante_id) values (nextval('produto_id_seq'), 'Camarão tailandês', '16 camarões grandes ao molho picante', 110, true, 1);
insert into produto (id, nome, descricao, preco, ativo, restaurante_id) values (nextval('produto_id_seq'), 'Salada picante com carne grelhada', 'Salada de folhas com cortes finos de carne bovina grelhada e nosso molho especial de pimenta vermelha', 87.20, true, 2);
insert into produto (id, nome, descricao, preco, ativo, restaurante_id) values (nextval('produto_id_seq'), 'Garlic Naan', 'Pão tradicional indiano com cobertura de alho', 21, true, 3);
insert into produto (id, nome, descricao, preco, ativo, restaurante_id) values (nextval('produto_id_seq'), 'Murg Curry', 'Cubos de frango preparados com molho curry e especiarias', 43, true, 3);
insert into produto (id, nome, descricao, preco, ativo, restaurante_id) values (nextval('produto_id_seq'), 'Bife Ancho', 'Corte macio e suculento, com dois dedos de espessura, retirado da parte dianteira do contrafilé', 79, true, 4);
insert into produto (id, nome, descricao, preco, ativo, restaurante_id) values (nextval('produto_id_seq'), 'T-Bone', 'Corte muito saboroso, com um osso em formato de T, sendo de um lado o contrafilé e do outro o filé mignon', 89, true, 4);
insert into produto (id, nome, descricao, preco, ativo, restaurante_id) values (nextval('produto_id_seq'), 'Sanduíche X-Tudo', 'Sandubão com muito queijo, hamburger bovino, bacon, ovo, salada e maionese', 19, true, 5);
insert into produto (id, nome, descricao, preco, ativo, restaurante_id) values (nextval('produto_id_seq'), 'Espetinho de Cupim', 'Acompanha farinha, mandioca e vinagrete', 8, true, 6);

insert into permissao (id, nome, descricao) values (1, 'CONSULTAR_COZINHAS', 'Permite consultar cozinhas');
insert into permissao (id, nome, descricao) values (2, 'EDITAR_COZINHAS', 'Permite editar cozinhas');

insert into grupo (id, nome) values (nextval('grupo_id_seq'), 'Gerente'), (nextval('grupo_id_seq'), 'Vendedor'), (nextval('grupo_id_seq'), 'Secretária'), (nextval('grupo_id_seq'), 'Cadastrador');

insert into grupo_has_permissao (grupo_id, permissao_id) values (1, 1), (1, 2), (2, 1), (2, 2), (3, 1);

insert into usuario (id, nome, email, senha, created_at, updated_at) values
    (nextval('usuario_id_seq'), 'Adriano Queiroz', 'adriano.nq+algafood@gmail.com', '123', current_timestamp, current_timestamp),
    (nextval('usuario_id_seq'), 'João da Silva', 'joao.ger@algafood.com', '123', current_timestamp, current_timestamp),
    (nextval('usuario_id_seq'), 'Maria Joaquina', 'maria.vnd@algafood.com', '123', current_timestamp, current_timestamp),
    (nextval('usuario_id_seq'), 'José Souza', 'jose.aux@algafood.com', '123', current_timestamp, current_timestamp),
    (nextval('usuario_id_seq'), 'Sebastião Martins', 'sebastiao.cad@algafood.com', '123', current_timestamp, current_timestamp);

insert into usuario_grupo (usuario_id, grupo_id) values (1, 1), (1, 2), (2, 2);

insert into restaurante_usuario_responsavel (restaurante_id, usuario_id) values (1, 5), (3, 5);

insert into pedido (id, restaurante_id, usuario_cliente_id, forma_pagamento_id, endereco_cidade_id, endereco_cep,
                    endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro,
                    status, created_at, updated_at, subtotal, taxa_frete, valor_total)
values (1, 1, 1, 1, 1, '38400-000', 'Rua Floriano Peixoto', '500', 'Apto 801', 'Brasil',
        'CRIADO', current_timestamp, current_timestamp, 298.90, 10, 308.90);

insert into item_pedido (id, pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao)
values (1, 1, 1, 1, 78.9, 78.9, null);

insert into item_pedido (id, pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao)
values (2, 1, 2, 2, 110, 220, 'Menos picante, por favor');


insert into pedido (id, restaurante_id, usuario_cliente_id, forma_pagamento_id, endereco_cidade_id, endereco_cep,
                    endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro,
                    status, created_at, updated_at, subtotal, taxa_frete, valor_total)
values (2, 4, 1, 2, 1, '38400-111', 'Rua Acre', '300', 'Casa 2', 'Centro',
        'CRIADO', current_timestamp, current_timestamp, 79, 0, 79);

insert into item_pedido (id, pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao)
values (3, 2, 6, 1, 79, 79, 'Ao ponto');

insert into pedido (id, codigo, restaurante_id, usuario_cliente_id, forma_pagamento_id, endereco_cidade_id, endereco_cep,
                    endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro,
                    status, created_at, confirmed_at, updated_at, delivered_at, subtotal, taxa_frete, valor_total)
values (3, 'b5741512-8fbc-47fa-9ac1-b530354fc0ff', 1, 1, 1, 1, '38400-222', 'Rua Natal', '200', null, 'Brasil',
        'ENTREGUE', '2019-10-30 21:10:00', '2019-10-30 21:10:45', '2019-10-30 21:55:44', '2019-10-30 21:55:44', 110, 10, 120);

insert into item_pedido (id, pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao)
values (4, 3, 2, 1, 110, 110, null);


insert into pedido (id, codigo, restaurante_id, usuario_cliente_id, forma_pagamento_id, endereco_cidade_id, endereco_cep,
                    endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro,
                    status, created_at, confirmed_at, updated_at, delivered_at, subtotal, taxa_frete, valor_total)
values (4, '5c621c9a-ba61-4454-8631-8aabefe58dc2', 1, 2, 1, 1, '38400-800', 'Rua Fortaleza', '900', 'Apto 504', 'Centro',
        'ENTREGUE', '2019-11-02 20:34:04', '2019-11-02 20:35:10', '2019-11-02 21:10:32', '2019-11-02 21:10:32', 174.4, 5, 179.4);

insert into item_pedido (id, pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao)
values (5, 4, 3, 2, 87.2, 174.4, null);


insert into pedido (id, codigo, restaurante_id, usuario_cliente_id, forma_pagamento_id, endereco_cidade_id, endereco_cep,
                    endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro,
                    status, created_at, confirmed_at, updated_at, delivered_at, subtotal, taxa_frete, valor_total)
values (5, '8d774bcf-b238-42f3-aef1-5fb388754d63', 1, 3, 2, 1, '38400-200', 'Rua 10', '930', 'Casa 20', 'Martins',
        'ENTREGUE', '2019-11-02 21:00:30', '2019-11-02 21:01:21', '2019-11-02 21:20:10', '2019-11-02 21:20:10', 87.2, 10, 97.2);

insert into item_pedido (id, pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao)
values (6, 5, 3, 1, 87.2, 87.2, null);