alter table restaurante add column aberto boolean not null default false;
update restaurante set aberto = false;