create table usuarios(

    id BIGSERIAL NOT NULL,
    login varchar(100) not null,
    senha varchar(255) not null,

    primary key(id)
);