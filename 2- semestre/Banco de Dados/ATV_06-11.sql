CREATE DATABASE atv2;
USE atv2;

CREATE TABLE IF NOT EXISTS clientes (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nome VARCHAR(100),
  telefone VARCHAR(11) 
);

INSERT INTO clientes (nome, telefone) VALUES
('Ana Silva', '11987654321'),
('Bruno Costa', '21912345678'),
('Carla Dias', '11955554444'),
('Daniel Moreira', '71988887777'),
('Eduarda Lima', '19977776666'),
('Fabio Melo', '11944443333');

-- atv 1

CREATE VIEW vw_lista_telefonica AS
SELECT
  nome,
  telefone
FROM
  clientes
ORDER BY
  nome;

SELECT * FROM vw_lista_telefonica;

-- atv 2
CREATE VIEW vw_clientes_sp AS
SELECT
  nome,
  telefone
FROM
  clientes
WHERE
  telefone LIKE '11%';

SELECT * FROM vw_clientes_sp;