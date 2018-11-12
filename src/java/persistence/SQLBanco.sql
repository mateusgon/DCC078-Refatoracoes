-- tipoPessoa = 1 (Cliente), 2 (Atendente), 3 (Chefe 01), 4 (Chefe 02), 5 (Chefe 03), 6 (Motoboy), 7 (SuperUsuarioRestaurante), 8 (SuperUsuarioSistema)
-- tipoProduto = 1 (Entrada), 2 (Prato Principal), 3 (Bebida), 4 (Sobremesa)
-- dificuldade = 1 (Fácil), 2 (Médio, 3 (Difícil)

create table restaurante (
restaurantecod integer primary key generated always as identity,
nome varchar(100),
nomeFantasia varchar(100),
telefone varchar(100),
endereco varchar(100),
sigla varchar(100),
ativado integer
);

create table pessoa (
pessoacod integer primary key generated always as identity,
nome varchar(100),
endereco varchar(100),
telefone varchar(100),
email varchar(100),
senha varchar(100),
restaurantecod integer,
tipoPessoa integer,
foreign key (restaurantecod) references restaurante (restaurantecod)
);

insert into pessoa (nome, email, senha, tipoPessoa) values ('adminGeral', 'adminGeral', 'adminGeral', 8);

create table produto (
produtocod integer primary key generated always as identity,
nome varchar(100),
valor double,
dificuldade integer,
tipoProduto integer,
restaurantecod integer,
notificado boolean,
ativado integer,
foreign key (restaurantecod) references restaurante (restaurantecod)
);


create table combo (
combocod integer primary key generated always as identity,
nome varchar(100),
valor double,
dificuldade integer,
restaurantecod integer,
ativado integer,
foreign key (restaurantecod) references restaurante (restaurantecod)
);

create table combo_produto (
codigo integer primary key generated always as identity,
combocod integer,
produtocod integer,
foreign key (combocod) references combo (combocod),
foreign key (produtocod) references produto (produtocod)
);

create table pedido (
pedidocod integer primary key generated always as identity,
estado integer,
valor double,
datapedido timestamp,
dificuldade integer,
restaurantecod integer,
pessoacod integer,
foreign key (restaurantecod) references restaurante (restaurantecod),
foreign key (pessoacod) references pessoa (pessoacod)
);

create table pedidomemento (
pedidomementocod integer primary key generated always as identity,
estado integer,
atual integer,
data timestamp,
pedidocod integer,
foreign key (pedidocod) references pedido (pedidocod)
);

create table pedido_produto (
codigo integer primary key generated always as identity,
pedidocod integer,
produtocod integer,
foreign key (pedidocod) references pedido (pedidocod),
foreign key (produtocod) references produto (produtocod)
);

create table pedido_combo (
codigo integer primary key generated always as identity,
pedidocod integer,
combocod integer,
foreign key (pedidocod) references pedido (pedidocod),
foreign key (combocod) references combo (combocod)
);

create table mensagem (
mensagemcod integer primary key generated always as identity,
mensagem varchar (1000),
pessoacod integer,
foreign key (pessoacod) references pessoa (pessoacod)
);