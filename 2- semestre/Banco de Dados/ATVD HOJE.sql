CREATE DATABASE Meritíssima21;
USE Meritíssima21;

CREATE TABLE Alunos (
    ID INT PRIMARY KEY auto_increment,
    Nome VARCHAR(100),
    Idade INT,
    Nota DECIMAL(5, 2)
);

INSERT INTO Alunos (ID, Nome, Idade, Nota) VALUES
(1, 'João', 18, 8.5),
(2, 'Maria', 20, 9.2),
(3, 'Carlos', 19, 7.8),
(4, 'Ana', 21, 8.9);

-- Criação da Stored Procedure ListarAlunos
DELIMITER //
CREATE PROCEDURE ListarAlunos()
BEGIN
    SELECT * FROM Alunos;
END //
DELIMITER ;
CALL ListarAlunos();

-- Criação da Stored Procedure InserirAluno
SELECT*FROM alunos
DELIMITER //

CREATE PROCEDURE InserirAluno(
    IN p_ID INT,
    IN p_Nome VARCHAR(100),
    IN p_Idade INT,
    IN p_Nota DECIMAL(5, 2)
)
BEGIN
    INSERT INTO Alunos (ID, Nome, Idade, Nota)
    VALUES (p_ID, p_Nome, p_Idade, p_Nota);
END //
DELIMITER ;

CALL InserirAlunos('John', 30, 8.5);
CALL InserirAlunos('Vanessa', 25, 9.2);
CALL InserirAlunos('Carlos', 45, 7.8);
CALL InserirAlunos('Ana', 21, 8.9);
--
CALL InserirAlunos('Lula', 26, 8.9);
CALL InserirAlunos('João', 22, 6.9);

-- Criação da Stored Procedure atualizarNota
DELIMITER //
CREATE PROCEDURE AtualizarNota(
    IN p_ID INT,
    IN p_NovaNota DECIMAL(5, 2)
)
BEGIN
    UPDATE Alunos
    SET Nota = p_NovaNota
    WHERE ID = p_ID;
END //
DELIMITER ;
CALL AtualizarNota(3, 8.2);

-- Criação da Stored Procedure ExcluirAluno
DELIMITER //
 CREATE PROCEDURE ExcluirAluno(
    IN p_ID INT
)
BEGIN
    DELETE FROM Alunos
    WHERE ID = p_ID;
END //
DELIMITER ;
CALL ExcluirAluno(1);

-- Criação da Stored Procedure ListarAlunosNotaMaiorQue6

DELIMITER //
CREATE PROCEDURE ListarAlunosNotaMaiorQue6()
BEGIN
    SELECT *
    FROM Alunos
    WHERE Nota > 6.0;
END //
DELIMITER ;
CALL ListarAlunosNotaMaiorQue6();