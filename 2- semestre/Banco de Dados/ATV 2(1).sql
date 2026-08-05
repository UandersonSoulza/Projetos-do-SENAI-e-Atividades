-- ATV 2
-- Calcular o número total de filmes cadastrados em nosso banco de dados.
-- Mostrar quantos títulos diferentes de filmes estão cadastrados em nosso banco de dados.
-- Calcular os valores mínimo, médio e máximo de uma locação em nossa locadora.

CREATE DATABASE locadora1;
use locadora1;

CREATE DATABASE locadora;
USE locadora;

CREATE TABLE clientes1(
cli_codigo INT
AUTO_INCREMENT PRIMARY KEY,
cli_nome VARCHAR(30),
cli_cpf VARCHAR(20),
cli_data_nasc DATE,
cli_sexo CHAR(1),
cli_email VARCHAR (50)
);

CREATE TABLE filmes(
fil_codigo INT
AUTO_INCREMENT PRIMARY KEY,
fil_titulo VARCHAR(40),
fil_genero VARCHAR(15),
fil_duracao TIME,
fil_situacao VARCHAR(12),
fil_preco NUMERIC(3,2)
);

select*from clientes1;
select*from filmes;

INSERT INTO clientes1 (cli_nome, cli_cpf, cli_data_nasc, cli_sexo, cli_email) VALUES
('Ana Clara', '111.222.333-44', '1995-03-15', 'F', 'ana.clara@email.com'),
('Bruno Martins', '222.333.444-55', '1988-07-22', 'M', 'bruno.@hotmail.com'),
('Carla Souza', '333.444.555-66', '2001-11-01', 'F', 'carla.souza@hotmail.com'),
('Diego Ferreira', '444.555.666-77', '1999-01-30', 'M', 'diego.f@email.com'),
('Eduarda Lima', '555.666.777-88', '1992-09-10', 'F', 'duda.lima@hotmail.com');

INSERT INTO filmes (fil_titulo, fil_genero, fil_duracao, fil_situacao, fil_preco) VALUES
('O Poderoso Chefão', 'Drama', '02:55:00', 'Disponível', 9.50),
('Interestelar', 'Ficção', '02:49:00', 'Alugado', 8.00),
('Cidade de Deus', 'Ação', '02:10:00', 'Disponível', 7.50),
('Parasita', 'Suspense', '02:12:00', 'Manutenção', 8.50),
('Parasita', 'Suspense', '02:12:00', 'Manutenção', 8.50);

SELECT SUM(fil_titulo) AS todos_filmes FROM filmes;

SELECT COUNT(DISTINCT fil_titulo ) AS filmes_ordem FROM filmes ;

SELECT MIN(fil_preco) AS preco_minimo FROM filmes;
SELECT MAX(fil_preco) AS preco_maximo FROM filmes;
SELECT AVG(fil_preco) AS preco_medio FROM filmes;










