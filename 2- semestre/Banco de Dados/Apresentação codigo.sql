-- TECHSTORE
-- demonstra a criação de um banco de dados completo,
-- incluindo tabelas, views, procedures, functions, triggers e
-- controle de usuários e transações.


-- 1. CRIAÇÃO DO BANCO DE DADOS
-- Primeiro, garantimos que o banco existe e o selecionamos para uso.

CREATE DATABASE techstore;
USE techstore;


-- 2. CRIAÇÃO DAS TABELAS
-- TABELA CLIENTE: Armazena os dados pessoais.
-- Destaques:
-- AUTO_INCREMENT: O banco gera o ID automaticamente (1, 2, 3)
-- UNIQUE no CPF: Garante que não cadastramos o mesmo CPF duas vezes.

CREATE TABLE IF NOT EXISTS CLIENTE (
    id_cliente INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cpf VARCHAR(14) UNIQUE NOT NULL, -- CPF não pode repetir
    email VARCHAR(100),
    telefone VARCHAR(15),
    data_cadastro DATE
);

-- TABELA PRODUTO: Armazena o estoque.
-- Destaques:
-- DECIMAL(10,2): Ideal para dinheiro (10 dígitos no total, 2 decimais).

CREATE TABLE IF NOT EXISTS PRODUTO (
    id_produto INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100),
    categoria VARCHAR(50),
    preco DECIMAL(10,2),
    estoque INT
);

-- TABELA PEDIDO: Registra a venda "cabeçalho".
-- Destaques:
-- FOREIGN KEY (Chave Estrangeira): Liga o Pedido ao Cliente que o fez.
-- Se o cliente não existir, o banco não deixa criar o pedido (Integridade Referencial).

CREATE TABLE IF NOT EXISTS PEDIDO (
    id_pedido INT AUTO_INCREMENT PRIMARY KEY,
    id_cliente INT,
    data_pedido DATE,
    valor_total DECIMAL(10,2),
    FOREIGN KEY (id_cliente) REFERENCES CLIENTE(id_cliente)
);

-- TABELA ITEM_PEDIDO: Registra os detalhes (quais produtos em cada pedido).
-- Destaques:
-- Relacionamento (N:N MUITOS PARA MUITOS): Liga Pedidos e Produtos.

CREATE TABLE IF NOT EXISTS ITEM_PEDIDO (
    id_item INT AUTO_INCREMENT PRIMARY KEY,
    id_pedido INT,
    id_produto INT,
    quantidade INT,
    valor_unitario DECIMAL(10,2),
    FOREIGN KEY (id_pedido) REFERENCES PEDIDO(id_pedido),
    FOREIGN KEY (id_produto) REFERENCES PRODUTO(id_produto)
);


-- 3. VIEWS 
-- O que é: Uma tabela virtual que simplifica consultas complexas.
-- Objetivo: Mostrar dados do Cliente junto com dados do Pedido sem precisar
-- escrever o JOIN toda vez.

CREATE OR REPLACE VIEW vw_pedidos_clientes AS
SELECT 
    c.nome AS Nome_Cliente,
    p.id_pedido AS ID_Pedido,
    p.data_pedido AS Data,
    p.valor_total AS Valor_Total
FROM 
    CLIENTE c
INNER JOIN 
    PEDIDO p ON c.id_cliente = p.id_cliente;

-- Como testar: SELECT * FROM vw_pedidos_clientes;

-- 4. STORED PROCEDURES PROCEDIMENTOS ARMAZENADOS
-- O que é: Um bloco de código salvo para executar tarefas repetitivas.
-- Objetivo: Facilitar o cadastro de produtos, recebendo os dados por parâmetros.
-- Nota: Usamos DELIMITER $$ para que o MySQL saiba onde termina a procedure.

DELIMITER $$

DROP PROCEDURE IF EXISTS sp_cadastrar_produto$$

CREATE PROCEDURE sp_cadastrar_produto(
    IN p_nome VARCHAR(100),
    IN p_categoria VARCHAR(50),
    IN p_preco DECIMAL(10,2),
    IN p_estoque INT
)
BEGIN
    INSERT INTO PRODUTO (nome, categoria, preco, estoque)
    VALUES (p_nome, p_categoria, p_preco, p_estoque);
END $$

DELIMITER ;

-- Como testar: CALL sp_cadastrar_produto('Mouse', 'Informática', 50.00, 100);


-- 5. STORED FUNCTIONS (FUNÇÕES)
-- O que é: Similar à procedure, mas SEMPRE retorna um valor.
-- Objetivo: Calcular o subtotal de um item (Quantidade * Preço Unitário).

DELIMITER $$

DROP FUNCTION IF EXISTS fn_valor_item$$

CREATE FUNCTION fn_valor_item(qtd INT, valor_unit DECIMAL(10,2))
RETURNS DECIMAL(10,2)
DETERMINISTIC
BEGIN
    DECLARE total DECIMAL(10,2);
    SET total = qtd * valor_unit;
    RETURN total;
END $$

DELIMITER ;

-- Como testar: SELECT fn_valor_item(2, 50.00) AS Subtotal;

-- 6. TRIGGERS 
-- O que é: Código automático disparado por eventos (INSERT, UPDATE, DELETE).
-- Objetivo: Baixar o estoque automaticamente quando um item é vendido.
-- Lógica:
-- Ocorre um INSERT na tabela ITEM_PEDIDO.
-- A trigger pega a quantidade vendida (NEW.quantidade).
-- A trigger atualiza a tabela PRODUTO subtraindo esse valor.

DELIMITER $$

DROP TRIGGER tr_atualiza_estoque$$

CREATE TRIGGER tr_atualiza_estoque
AFTER INSERT ON ITEM_PEDIDO
FOR EACH ROW
BEGIN
    UPDATE PRODUTO
    SET estoque = estoque - NEW.quantidade
    WHERE id_produto = NEW.id_produto;
END $$

DELIMITER ;

-- 7. CONTROLE DE TRANSAÇÃO 
-- ----------------------------------------------------------
-- Objetivo: Demonstrar o conceito de desfazer operações (ROLLBACK).
-- Cenário: Simulamos uma atualização de estoque errada e a cancelamos.
START TRANSACTION;
    -- Tenta diminuir 5 do estoque do produto 1
    UPDATE PRODUTO SET estoque = estoque - 5 WHERE id_produto = 1;
    -- O comando abaixo desfaz tudo que foi feito desde o START TRANSACTION.
ROLLBACK; 


-- 8. SEGURANÇA E USUÁRIOS 
-- Criação de Usuário
-- Cria um usuário chamado 'operador' que só acessa desta máquina (localhost).
CREATE USER IF NOT EXISTS 'operador'@'localhost' IDENTIFIED BY '12345';

-- Conceder Permissões (GRANT)
-- O operador só pode INSERIR dados na tabela PRODUTO. Ele não pode apagar nem ver clientes.
GRANT INSERT ON techstore.PRODUTO TO 'operador'@'localhost';

-- Revogar Permissões (REVOKE)
-- Removemos TODOS os privilégios do usuário, deixando-o sem acesso a nada.
-- Nota: A sintaxe correta para remover tudo não especifica o banco 'ON ...'.
REVOKE ALL PRIVILEGES, GRANT OPTION FROM 'operador'@'localhost';
