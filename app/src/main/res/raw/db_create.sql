CREATE TABLE IF NOT EXISTS TB_LOGIN(
    username varchar NOT NULL PRIMARY KEY ,
    password varchar (10) NOT NULL
);


DROP TABLE TB_UNIDADE;
CREATE TABLE TB_UNIDADE(
    id integer NOT NULL PRIMARY KEY AUTOINCREMENT,
    nome varchar (255) NOT NULL,
    cidade varchar (255) NOT NULL,
    endereco varchar (255) NOT NULL,
    telefone varchar (255) NOT NULL,
    horarioFuncionamento varchar (255) NOT NULL,
    urlImagem varchar (255) NOT NULL,
    latitude varchar (255) NOT NULL,
    longitude varchar (255) NOT NULL
);
