CREATE DATABASE AutoDrive;
USE AutoDrive;

CREATE TABLE Cliente (
    id_cliente INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(255) NOT NULL,
    cpf VARCHAR(14) NOT NULL, -- Removido o UNIQUE daqui também, caso não tenha sido ensinado
    telefone VARCHAR(20),
    email VARCHAR(255)
);

CREATE TABLE Funcionario (
    id_funcionario INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(255) NOT NULL,
    cargo VARCHAR(100),
    salario DECIMAL(10, 2)
);

CREATE TABLE Veiculo (
    id_veiculo INT PRIMARY KEY AUTO_INCREMENT,
    modelo VARCHAR(100) NOT NULL,
    marca VARCHAR(50) NOT NULL,
    ano INT,
    preco DECIMAL(10, 2) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'disponível' -- 'disponível' ou 'vendido'
);

CREATE TABLE Venda (
    id_venda INT PRIMARY KEY AUTO_INCREMENT,
    data DATE NOT NULL,
    id_cliente INT,
    id_funcionario INT,
    id_veiculo INT, -- Coluna da chave estrangeira
    valor_final DECIMAL(10, 2) NOT NULL,
    
    FOREIGN KEY (id_cliente) REFERENCES Cliente(id_cliente),
    FOREIGN KEY (id_funcionario) REFERENCES Funcionario(id_funcionario),
    FOREIGN KEY (id_veiculo) REFERENCES Veiculo(id_veiculo)
    
);

INSERT INTO Cliente (nome, cpf, telefone, email) VALUES
('Ana Silva', '111.111.111-11', '11987654321', 'ana@email.com'),
('Bruno Costa', '222.222.222-22', '21912345678', NULL),
('Carla Dias', '333.333.333-33', NULL, 'carla@email.com'),
('Daniel Moreira', '444.444.444-44', '31998877665', 'daniel@email.com');

INSERT INTO Funcionario (nome, cargo, salario) VALUES
('Ricardo Borges', 'Vendedor', 3500.00),
('Sofia Lima', 'Vendedor', 3500.00),
('Marcos Andrade', 'Gerente', 7000.00);

INSERT INTO Veiculo (modelo, marca, ano, preco, status) VALUES
('Corolla', 'Toyota', 2023, 130000.00, 'vendido'),
('Civic', 'Honda', 2022, 115000.00, 'vendido'),
('Onix', 'Chevrolet', 2021, 68000.00, 'vendido'),
('Hilux', 'Toyota', 2023, 220000.00, 'disponível'),
('HR-V', 'Honda', 2024, 150000.00, 'disponível'),
('Kwid', 'Renault', 2022, 45000.00, 'disponível'),
('RAV4', 'Toyota', 2021, 190000.00, 'vendido');

INSERT INTO Venda (data, id_cliente, id_funcionario, id_veiculo, valor_final) VALUES
('2024-01-15', 1, 1, 1, 128000.00), -- Ana, Ricardo, Corolla
('2024-02-10', 4, 2, 2, 114000.00), -- Daniel, Sofia, Civic
('2024-03-05', 1, 1, 3, 67000.00),  -- Ana, Ricardo, Onix (Média da Chevrolet < 70k)
('2024-04-20', 2, 2, 7, 185000.00); -- Bruno, Sofia, RAV4

-- 1. Clientes com telefone e e-mail cadastrados Liste o nome, telefone e e-mail de todos os clientes que possuam telefone e e-mail preenchidos. Organize o resultado por nome (A-Z).
SELECT
nome,telefone,email
FROM
Cliente
WHERE
telefone IS NOT NULL AND email IS NOT NULL
ORDER BY
nome ASC;

-- 2. Veículos disponíveis por marca específica Exiba modelo, marca e preço dos veículos disponíveis cuja marca seja Toyota ou Honda. Ordene por preço (maior para menor).
SELECT
modelo,marca,preco
FROM
Veiculo
WHERE 
status = 'disponível' AND (marca = 'Toyota' OR marca = 'Honda')
ORDER BY
preco DESC;
    
-- 3. Veículos não vendidos e acima de 80 mil Mostre modelo, marca e preço dos veículos que não estejam vendidos (status = 'disponível') e tenham preço superior a R$ 80.000,00. Ordene por preço (maior para menor).
SELECT
modelo,marca,preco
FROM
Veiculo
WHERE
    status = 'disponível' AND preco > 80000.00
ORDER BY
preco DESC;

-- 4. Quantidade total de veículos cadastrados Informe a quantidade total de registros existentes na tabela Veiculo.
SELECT
COUNT(*) AS Quantidade_Total_Veiculos
FROM
Veiculo;

-- 5. Estatísticas de valores de venda Apresente a média, o menor e o maior valor_final entre todas as vendas registradas.
SELECT
AVG(valor_final) AS Media_Valor_Venda,
MIN(valor_final) AS Menor_Valor_Venda, MAX(valor_final) AS Maior_Valor_Venda
FROM
Venda;
    
-- 6. Faturamento por funcionário Liste, para cada funcionário, o total vendido (soma de valor_final) considerando as vendas realizadas por ele. Ordene do maior total para o menor.
SELECT
F.nome AS Nome_Funcionario,
SUM(V.valor_final) AS Total_Vendido
FROM
Funcionario AS F
INNER JOIN 
Venda AS V ON F.id_funcionario = V.id_funcionario
GROUP BY
F.id_funcionario, F.nome 
ORDER BY
Total_Vendido DESC;
    
-- 7. Detalhe de vendas (cliente, funcionário e veículo) Produza um relatório contendo id da venda, data, nome do cliente, nome do funcionário, modelo do veículo e valor_final. Ordene da venda mais recente para a mais antiga.
SELECT
V.id_venda,V.data,
C.nome AS Nome_Cliente,
F.nome AS Nome_Funcionario,
VE.modelo AS Modelo_Veiculo,
V.valor_final
FROM
Venda AS V
INNER JOIN
Cliente AS C ON V.id_cliente = C.id_cliente
INNER JOIN
Funcionario AS F ON V.id_funcionario = F.id_funcionario
INNER JOIN
Veiculo AS VE ON V.id_veiculo = VE.id_veiculo
ORDER BY
V.data DESC;
    
-- 8. Funcionários com ou sem vendas (LEFT JOIN) Liste todos os funcionários e, quando houver, os ids das vendas e seus valores associados. Funcionários sem vendas também devem aparecer no resultado. Ordene por nome do funcionário.
SELECT
F.nome AS Nome_Funcionario,
V.id_venda,
V.valor_final
FROM
Funcionario AS F
LEFT JOIN
Venda AS V ON F.id_funcionario = V.id_funcionario
ORDER BY
F.nome ASC;
    
-- 9. Todas as vendas com marca do veículo (RIGHT JOIN) Exiba id da venda, marca e modelo do veículo e valor_final para todas as vendas registradas, garantindo que toda venda apareça mesmo se algum dado do veículo não estiver presente. Ordene por valor_final (maior para menor).
SELECT
V.id_venda,
VE.marca,
VE.modelo,
V.valor_final
FROM
Veiculo AS VE
RIGHT JOIN
Venda AS V ON VE.id_veiculo = V.id_veiculo
ORDER BY
V.valor_final DESC;
    
-- 10. Resumo por marca com filtro de média Agrupe as vendas por marca do veículo e mostre o total de veículos vendidos e a média do valor_final por marca. Exiba apenas as marcas cuja média de valor_final seja superior a R$ 70.000,00. Ordene da maior média para a menor.
SELECT
VE.marca,
COUNT(V.id_venda) AS Total_Veiculos_Vendidos,
AVG(V.valor_final) AS Media_Valor_Final
FROM
Venda AS V
INNER JOIN
Veiculo AS VE ON V.id_veiculo = VE.id_veiculo
GROUP BY
VE.marca
HAVING
AVG(V.valor_final) > 70000.00
ORDER BY
Media_Valor_Final DESC;





