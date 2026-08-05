CREATE DATABASE aula12;
USE aula12;

-- Tabela de Membros de Equipe
CREATE TABLE MembrosEquipe (
    membro_id INT PRIMARY KEY AUTO_INCREMENT,
    nome_membro VARCHAR(100) NOT NULL,
    funcao VARCHAR(50),
    data_contratacao DATE
);

-- Tabela de Projetos
CREATE TABLE Projetos (
    projeto_id INT PRIMARY KEY AUTO_INCREMENT,
    nome_projeto VARCHAR(100) NOT NULL,
    data_inicio DATE,
    data_fim_prevista DATE,
    gerente_id INT, -- Chave estrangeira para o gerente do projeto (um membro da equipe)
    FOREIGN KEY (gerente_id) REFERENCES MembrosEquipe(membro_id)
);

-- Tabela de Alocacoes (Tabela de ligação para muitos-para-muitos, ou seja, um membro pode estar em vários projetos e um projeto pode ter vários membros)
CREATE TABLE Alocacoes (
    alocacao_id INT PRIMARY KEY AUTO_INCREMENT,
    membro_id INT NOT NULL,
    projeto_id INT NOT NULL,
    horas_semanais INT,
    FOREIGN KEY (membro_id) REFERENCES MembrosEquipe(membro_id),
    FOREIGN KEY (projeto_id) REFERENCES Projetos(projeto_id)
);

INSERT INTO MembrosEquipe (nome_membro, funcao, data_contratacao) VALUES
('João Silva', 'Desenvolvedor', '2022-01-10'),
('Maria Oliveira', 'Gerente de Projetos', '2020-05-15'),
('Pedro Souza', 'Analista de QA', '2023-03-20'),
('Ana Paula', 'Designer UX/UI', '2022-11-01');

INSERT INTO Projetos (nome_projeto, data_inicio, data_fim_prevista, gerente_id) VALUES
('App Mobile Vendas', '2024-01-01', '2024-06-30', 2),  -- Gerente: Maria Oliveira (ID 2)
('Plataforma Web RH', '2023-09-01', '2024-03-31', 2),   -- Gerente: Maria Oliveira (ID 2)
('Otimização de Banco de Dados', '2024-02-15', '2024-05-30', 1), -- Gerente: João Silva (ID 1)
('Redesign do Site Institucional', '2024-04-01', '2024-07-31', 4); -- Gerente: Ana Paula (ID 4)

INSERT INTO Alocacoes (membro_id, projeto_id, horas_semanais) VALUES
(1, 1, 20), -- João Silva no App Mobile Vendas
(1, 3, 15), -- João Silva na Otimização de BD
(2, 1, 10), -- Maria Oliveira (além de gerente) no App Mobile Vendas
(3, 2, 30); -- Pedro Souza na Plataforma Web RH

-- Liste todos os projetos e seus respectivos gerentes. A consulta deve mostrar o nome do projeto e o nome do membro da equipe que o gerencia.
SELECT 
P.nome_projeto AS Nome_Projeto,
M.nome_membro AS Nome_Gerente 
FROM 
Projetos AS P INNER JOIN MembrosEquipe AS M ON P.gerente_id = M.membro_id;

-- Liste todos os membros da equipe e os projetos em que estão alocados. (LEFT JOIN) É crucial que todos os membros da equipe apareçam na lista, 
-- mesmo aqueles que não estão atualmente alocados em nenhum projeto. Se um membro não estiver alocado, a coluna do projeto deve ser NULL.

SELECT 
M.nome_membro AS Nome_Membro, 
P.nome_projeto AS Nome_Projeto 
FROM 
MembrosEquipe AS M LEFT JOIN Alocacoes AS A ON M.membro_id = A.membro_id LEFT JOIN Projetos AS P ON A.projeto_id = P.projeto_id;

-- Liste todos os projetos e os membros da equipe que estão alocados neles. (RIGHT JOIN) É essencial que todos os projetos apareçam na lista, 
-- mesmo aqueles que ainda não têm nenhum membro de equipe alocado. Se um projeto não tiver membros alocados, a coluna do membro deve ser NULL.

SELECT 
M.nome_membro AS Nome_Membro, P.nome_projeto AS Nome_Projeto 
FROM 
MembrosEquipe AS M INNER JOIN Alocacoes AS A ON M.membro_id = A.membro_id RIGHT JOIN Projetos AS P ON A.projeto_id = P.projeto_id;