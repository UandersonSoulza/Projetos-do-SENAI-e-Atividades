CREATE DATABASE IF NOT EXISTS LOLIPOP;
USE LOLIPOP;

CREATE TABLE IF NOT EXISTS produtos (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nome VARCHAR(100) NOT NULL,
  preco DECIMAL(10,2) NOT NULL,
  data_cadastro DATETIME
);

CREATE TABLE IF NOT EXISTS historico_precos (
  id INT AUTO_INCREMENT PRIMARY KEY,
  id_produto INT NOT NULL,
  preco_anterior DECIMAL(10,2),
  data_alteracao DATETIME,
  FOREIGN KEY (id_produto) REFERENCES produtos(id)
);

CREATE TABLE IF NOT EXISTS log_exclusao (
  id INT AUTO_INCREMENT PRIMARY KEY,
  id_produto INT,
  nome_produto VARCHAR(100),
  data_exclusao DATETIME
);

CREATE TABLE IF NOT EXISTS auditoria_insercoes (
  id INT AUTO_INCREMENT PRIMARY KEY,
  id_produto INT,
  nome VARCHAR(100),
  preco DECIMAL(10,2)
);

CREATE TABLE IF NOT EXISTS log_alteracoes (
  id INT AUTO_INCREMENT PRIMARY KEY,
  id_produto INT,
  nome VARCHAR(100),
  preco_antigo DECIMAL(10,2),
  preco_novo DECIMAL(10,2),
  data_alteracao DATETIME
);

INSERT INTO produtos (nome, preco) VALUES
('Smartphone Galaxy S23', 3999.00),
('Notebook Dell i7', 5899.90),
('Monitor LG 24"', 899.00),
('Mouse Gamer Logitech', 249.90),
('Teclado Mecanico Redragon', 359.50)

-- Questao 1 - Registro de exclusões (AFTER DELETE)
-- Voce esta desenvolvendo um sistema que mantem um historico dos produtos excluidos da tabela `produtos`.
-- Crie uma trigger `AFTER DELETE` que armazene o `id`, `nome` e a data da exclusao em uma tabela
-- chamada `log_exclusao`.
-- a) Crie o script da tabela `log_exclusao`.
-- b) Escreva o codigo da trigger necessaria para registrar a exclusao.


DELIMITER //

CREATE TRIGGER tr_log_exclusao
AFTER DELETE ON produtos
FOR EACH ROW
BEGIN
  INSERT INTO log_exclusao (id_produto, nome_produto, data_exclusao)
  VALUES (OLD.id, OLD.nome, NOW());
END //

DELIMITER ;

-- Questao 2 - Copia para auditoría (AFTER INSERT)
-- Ao cadastrar um novo produto na tabela `produtos`, o sistema precisa manter uma copia na tabela
-- `auditoria_insercoes`.
-- a) Explique por que uma trigger `AFTER INSERT` seria adequada nesse caso.
-- b) Crie a trigger que insere o `id`, `nome` e `preco` na tabela `auditoria_insercoes`.

DELIMITER //

CREATE TRIGGER tr_auditoria_insercoes
AFTER INSERT ON produtos
FOR EACH ROW
BEGIN
  INSERT INTO auditoria_insercoes (id_produto, nome, preco)
  VALUES (NEW.id, NEW.nome, NEW.preco);
END //

DELIMITER ;

-- Questao 3 - Registro de atualização (AFTER UPDATE)
-- Implemente uma trigger `AFTER UPDATE` para registrar alteracoes feitas na tabela `produtos`. Sempre que
-- um produto for alterado, a trigger deve salvar os dados anteriores e novos em uma tabela `log_alteracoes`.
-- a) Quais dados devem ser registrados para garantir rastreabilidade?

DELIMITER //

CREATE TRIGGER tr_log_alteracoes
AFTER UPDATE ON produtos
FOR EACH ROW
BEGIN
    INSERT INTO log_alteracoes (id_produto, nome, preco_antigo, preco_novo, data_alteracao)
    VALUES (NEW.id, NEW.nome, OLD.preco, NEW.preco, NOW());
END //

DELIMITER ;


-- Questao 4 - Preenchimento automático (BEFORE INSERT)
-- Crie uma trigger `BEFORE INSERT` para a tabela `produtos`, que automaticamente define a `data_cadastro`
-- com a data e hora atual (`NOW()`), mesmo que o usuario nao preencha esse campo.
-- a) Por que essa trigger deve ser do tipo `BEFORE`?
-- b) Escreva o codigo da trigger.

DELIMITER //

CREATE TRIGGER tr_preenche_data_cadastro
BEFORE INSERT ON produtos
FOR EACH ROW
BEGIN
  SET NEW.data_cadastro = NOW();
END //

DELIMITER ;

-- Questao 5 - Validação de valor (BEFORE INSERT)
-- A loja nao permite cadastro de produtos com preco menor ou igual a zero.
-- a) Crie uma trigger `BEFORE INSERT` que impeça o cadastro invalido e retorne uma mensagem de erro
-- personalizada.
-- b) Explique o que acontece se tentarmos inserir um produto com preco igual a zero.

DELIMITER //

CREATE TRIGGER tr_valida_preco_positivo
BEFORE INSERT ON produtos
FOR EACH ROW
BEGIN
  IF NEW.preco <= 0 THEN
    SIGNAL SQLSTATE '45000'
    SET MESSAGE_TEXT = 'Erro: O preço do produto não pode ser menor ou igual a zero.';
  END IF;
END //

DELIMITER ;

-- Questao 6 - Proteção contra atualizacoes inválidas (BEFORE UPDATE)
-- O gerente determinou que o preco de um produto nao pode ser reduzido em mais de 50% do valor original.
-- a) Escreva uma trigger `BEFORE UPDATE` que verifique essa condicao.
-- b) Explique o que ocorre se a condicao for violada.

DELIMITER //

CREATE TRIGGER tr_valida_desconto_maximo
BEFORE UPDATE ON produtos
FOR EACH ROW
BEGIN
  IF NEW.preco < (OLD.preco * 0.50) THEN
    SIGNAL SQLSTATE '45000'
    SET MESSAGE_TEXT = 'Erro: A atualização foi bloqueada. O preço não pode ser reduzido em mais de 50%.';
  END IF;
END //

DELIMITER ;