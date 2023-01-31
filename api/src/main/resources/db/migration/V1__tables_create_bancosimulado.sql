create table agencia (
                         id bigserial not null primary key,
                         cep varchar(255),
                         nome varchar(255),
                         numero varchar(255),
                         numero_predio bigint,
                         rua varchar(255)
);

create table cliente (
                         id bigserial not null primary key,
                         cpf varchar(255),
                         data_nascimento date,
                         idade integer,
                         nome varchar(255)
);

create table conta (
                       id bigserial not null primary key,
                       numero bigint,
                       saldo numeric(38,2),
                       tipo_conta smallint,
                       agencia bigint,
                       cliente bigint
);

create table gerente (
                         id bigserial not null primary key,
                         cpf varchar(255),
                         data_nascimento date,
                         idade integer,
                         nome varchar(255),
                         status_trabalho varchar(255),
                         agencia bigint
);

create table transacao (
                           id bigserial not null primary key,
                           data_transacao date,
                           tipo_operacao varchar(255),
                           valor numeric(38,2),
                           conta_destino bigint,
                           conta_operadora bigint
);

alter table if exists conta
    add constraint FKbqhgt65tsnan8fjowibry14sp
    foreign key (agencia)
    references agencia;


alter table if exists conta
    add constraint FK9e7hbr87sjmx6lk2fmoeeb87y
    foreign key (cliente)
    references cliente;


alter table if exists gerente
    add constraint FK7qt2rmqt33vvoeupy92k2qfo6
    foreign key (agencia)
    references agencia;


alter table if exists transacao
    add constraint FKrqm4qdce8dn0ivswg0pg9rt4i
    foreign key (conta_destino)
    references conta;


alter table if exists transacao
    add constraint FKgf6utamblfvrrvflj9s7ucyyk
    foreign key (conta_operadora)
    references conta;