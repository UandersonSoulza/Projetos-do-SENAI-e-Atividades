CREATE DATABASE aula21;
USE aula21;

CREATE TABLE cliente (
  idcliente INTEGER UNSIGNED    AUTO_INCREMENT,
  nome_cliente VARCHAR(255)  NULL  ,
  email VARCHAR(255)  NULL    ,
PRIMARY KEY(idcliente));

CREATE TABLE compras (
  idcompras INTEGER UNSIGNED    AUTO_INCREMENT,
  produtos_idprodutos INTEGER UNSIGNED    ,
  cliente_idcliente INTEGER UNSIGNED    ,
  quantidade INTEGER UNSIGNED  NULL    ,
PRIMARY KEY(idcompras)  ,
INDEX compras_FKIndex1(cliente_idcliente)  ,
INDEX compras_FKIndex2(produtos_idprodutos));

CREATE TABLE produtos (
  idprodutos INTEGER UNSIGNED    AUTO_INCREMENT,
  categoria_idcategoria INTEGER UNSIGNED   ,
  nome_produto VARCHAR(255)  NULL  ,
  preco FLOAT  NULL    ,
PRIMARY KEY(idprodutos)  ,
INDEX produtos_FKIndex1(categoria_idcategoria));

CREATE TABLE categoria (
  idcategoria INTEGER UNSIGNED   AUTO_INCREMENT,
  nome_categoria VARCHAR(255)      ,
PRIMARY KEY(idcategoria));

-- Inserindo dados na tabela clientes com email nulo
INSERT INTO cliente (nome_cliente, email) VALUES
  ('Fernando Oliveira', NULL),
  ('Gabriela Santos', 'gabriela@email.com'),
  ('Daniel Silva', NULL);

-- Inserindo dados na tabela produtos com preco nulo e categoria nula
INSERT INTO produtos (nome_produto, preco,  categoria_idcategoria) VALUES
  ('Mochila', NULL, 1),
  ('Tablet', 299.99, NULL),
  ('Óculos de Sol', 79.99, 2);

-- Inserindo dados na tabela compras com id_cliente nulo e quantidade nula
INSERT INTO compras (cliente_idcliente, produtos_idprodutos, quantidade) VALUES
  (NULL, 2, 1),
  (NULL, 1, NULL),
  (2, 3, 2),
  (3, NULL, 3);

-- Inserindo dados na tabela categorias com nome nulo
INSERT INTO categoria (nome_categoria) VALUES
  ('Roupas'),
  ('Eletrônicos'),
  (NULL);
  
  
  
  -- Retorna o nome do cliente, produto, quantidade e preço da compra (INNER JOIN) 
SELECT 
C.nome_cliente AS Nome_Cliente,
P.nome_produto AS Nome_Produto,
CM.quantidade AS Quantidade,
P.preco AS Preco 
FROM
cliente AS C INNER JOIN compras AS CM ON C.idcliente = CM.cliente_idcliente INNER JOIN produtos AS P ON CM.produtos_idprodutos = P.idprodutos;

-- Retorna todos os clientes e detalhes da compra se houver(LEFT JOIN) 
SELECT 
C.nome_cliente AS Nome_Cliente,
CM.idcompras AS ID_Compra, 
CM.produtos_idprodutos AS ID_Produto, 
CM.quantidade AS Quantidade 
FROM 
cliente AS C LEFT JOIN compras AS CM ON C.idcliente = CM.cliente_idcliente;

-- Retorna todas as compras e nome do cliente se aplicável(RIGHT JOIN) 
SELECT 
C.nome_cliente AS Nome_Cliente,
CM.idcompras AS ID_Compra, 
CM.produtos_idprodutos AS ID_Produto, 
CM.quantidade AS Quantidade 
FROM 
cliente AS C RIGHT JOIN compras AS CM ON C.idcliente = CM.cliente_idcliente;

-- Retorna o nome da categoria e o nome dos produtos pertencentes a essa categoria(INNER JOIN) 
SELECT 
CA.nome_categoria AS Nome_Categoria, 
P.nome_produto AS Nome_Produto 
FROM
categoria AS CA INNER JOIN produtos AS P ON CA.idcategoria = P.categoria_idcategoria;

-- Retorna o nome da categoria e o nome dos produtos, incluindo categorias sem produtos(LEFT JOIN) 
SELECT 
CA.nome_categoria AS Nome_Categoria, 
P.nome_produto AS Nome_Produto 
FROM 
categoria AS CA LEFT JOIN produtos AS P ON CA.idcategoria = P.categoria_idcategoria;

-- Retorna o nome da categoria e o nome dos produtos, incluindo produtos sem categoria(RIGHT JOIN) 
SELECT 
CA.nome_categoria AS Nome_Categoria,
P.nome_produto AS Nome_Produto 
FROM 
categoria AS CA RIGHT JOIN produtos AS P ON CA.idcategoria = P.categoria_idcategoria;
  
  
  
  
