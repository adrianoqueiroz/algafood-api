insert into cozinha (id, nome) values (1, 'Tailandesa');
insert into cozinha (id, nome) values (2, 'Indiana');

insert into restaurante (nome, taxa_frete, cozinha_id) values ('Thai Gourmet', 10, 1);
insert into restaurante (nome, taxa_frete, cozinha_id) values ('Thai Delivery', 10, 1);
insert into restaurante (nome, taxa_frete, cozinha_id) values ('Tuk Tuk Comida Indiana', 10, 2);

insert into forma_pagamento (id, descricao) values (1, 'Cartão de Crédito');
insert into forma_pagamento (id, descricao) values (2, 'PIX');
insert into forma_pagamento (id, descricao) values (3, 'DOC');
insert into forma_pagamento (id, descricao) values (4, 'TED');

insert into permissao (id, nome, descricao) values (1, 'Consultar', 'Permite consultar dados');
insert into permissao (id, nome, descricao) values (2, 'Cadastrar', 'Permite cadastrar dados');
insert into permissao (id, nome, descricao) values (3, 'Atualizar', 'Permite atualizar dados');
insert into permissao (id, nome, descricao) values (4, 'Excluir', 'Permite excluir dados');

insert into estado (id, nome, sigla) values (1, 'Acre', 'AC');
insert into estado (id, nome, sigla) values (2, 'Alagoas', 'AL');
insert into estado (id, nome, sigla) values (3, 'Amapá', 'AP');
insert into estado (id, nome, sigla) values (4, 'Amazonas', 'AM');
insert into estado (id, nome, sigla) values (5, 'Bahia', 'BA');
insert into estado (id, nome, sigla) values (6, 'Ceará', 'CE');
insert into estado (id, nome, sigla) values (7, 'Distrito Federal', 'DF');
insert into estado (id, nome, sigla) values (8, 'Espírito Santo', 'ES');
insert into estado (id, nome, sigla) values (9, 'Goiás', 'GO');
insert into estado (id, nome, sigla) values (10, 'Maranhão', 'MA');
insert into estado (id, nome, sigla) values (11, 'Mato Grosso', 'MT');
insert into estado (id, nome, sigla) values (12, 'Mato Grosso do Sul', 'MS');
insert into estado (id, nome, sigla) values (13, 'Minas Gerais', 'MG');
insert into estado (id, nome, sigla) values (14, 'Pará', 'PA');
insert into estado (id, nome, sigla) values (15, 'Paraíba', 'PB');
insert into estado (id, nome, sigla) values (16, 'Paraná', 'PR');
insert into estado (id, nome, sigla) values (17, 'Pernambuco', 'PE');
insert into estado (id, nome, sigla) values (18, 'Piauí', 'PI');
insert into estado (id, nome, sigla) values (19, 'Rio de Janeiro', 'RJ');
insert into estado (id, nome, sigla) values (20, 'Rio Grande do Norte', 'RN');
insert into estado (id, nome, sigla) values (21, 'Rio Grande do Sul', 'RS');
insert into estado (id, nome, sigla) values (22, 'Rondônia', 'RO');
insert into estado (id, nome, sigla) values (23, 'Roraima', 'RR');
insert into estado (id, nome, sigla) values (24, 'Santa Catarina', 'SC');
insert into estado (id, nome, sigla) values (25, 'São Paulo', 'SP');
insert into estado (id, nome, sigla) values (26, 'Sergipe', 'SE');
insert into estado (id, nome, sigla) values (27, 'Tocantins', 'TO');

insert into cidade (id, nome, estado_id) values (1, 'Abaíra', 1);
insert into cidade (id, nome, estado_id) values (2, 'Salvador', 5);
insert into cidade (id, nome, estado_id) values (3, 'Brotas de Macaúbas', 5);
insert into cidade (id, nome, estado_id) values (4, 'São Paulo', 25);
