CREATE DATABASE atv1;
USE atv1;

CREATE TABLE clientes (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nome VARCHAR(100)
);

CREATE TABLE pedidos (
  id INT AUTO_INCREMENT PRIMARY KEY,
  id_cliente INT,
  valor DECIMAL(10,2),
  FOREIGN KEY (id_cliente) REFERENCES clientes(id)
);

INSERT INTO clientes (nome) VALUES
('Ana Silva'),
('Bruno Costa'),
('Carla Dias');

INSERT INTO pedidos (id_cliente, valor) VALUES
(1, 100.00),
(1, 250.50),
(2, 80.00),
(1, 150.00);

-- atv 1

DELIMITER $$

CREATE FUNCTION boas_vindas()
RETURNS VARCHAR(100)
DETERMINISTIC
BEGIN
RETURN 'Olá! Seja bem-vindo ao sistema de pedidos.';
END $$

DELIMITER ;

SELECT boas_vindas() AS mensagem;


-- atv 2

DELIMITER $$

CREATE FUNCTION total_pedidos()
RETURNS INT
DETERMINISTIC
BEGIN
  DECLARE total INT;
  SELECT COUNT(*) INTO total FROM pedidos;
  RETURN total;
END $$

DELIMITER ;

SELECT total_pedidos() AS quantidade_total_de_pedidos;


-- atv 3

DELIMITER $$

CREATE FUNCTION contar_pedidos(cliente_id INT)
RETURNS INT
DETERMINISTIC
BEGIN
  DECLARE total_cliente INT;
  SELECT COUNT(*) INTO total_cliente FROM pedidos WHERE id_cliente = cliente_id;
  RETURN total_cliente;
END $$

DELIMITER ;

SELECT contar_pedidos(1) AS pedidos_cliente_1;
SELECT contar_pedidos(3) AS pedidos_cliente_3;

-- atv 4

DELIMITER $$

CREATE FUNCTION total_gasto_cliente(cliente_id INT)
RETURNS DECIMAL(10,2)
DETERMINISTIC
BEGIN
  DECLARE valor_total DECIMAL(10,2);
  SELECT COALESCE(SUM(valor), 0.00) INTO valor_total FROM pedidos WHERE id_cliente = cliente_id;
  RETURN valor_total;
END $$

DELIMITER ;

SELECT total_gasto_cliente(1) AS gasto_cliente_1;
SELECT total_gasto_cliente(3) AS gasto_cliente_3;

-- atv 5
SELECT
  nome,
  total_gasto_cliente(id) AS total_gasto
FROM
  clientes;