-- Script de migração para corrigir a constraint de movement_logs.product_id
-- Use este script se preferir corrigir o banco manualmente (a aplicação também
-- faz essa correção automaticamente ao iniciar, em ConexaoBancoDados.initializeDatabase()).
--
-- Este script descobre o NOME REAL da constraint automaticamente, em vez de
-- assumir um nome fixo (que pode variar dependendo de como a tabela foi criada).

USE controle_estoque;

SET @constraint_name = (
    SELECT CONSTRAINT_NAME
    FROM information_schema.REFERENTIAL_CONSTRAINTS
    WHERE CONSTRAINT_SCHEMA = 'controle_estoque'
      AND TABLE_NAME = 'movement_logs'
      AND REFERENCED_TABLE_NAME = 'products'
    LIMIT 1
);

-- 1. Permitir NULL em product_id
ALTER TABLE movement_logs MODIFY COLUMN product_id INT NULL;

-- 2. Remover a constraint de FK existente (nome descoberto dinamicamente acima)
SET @drop_sql = CONCAT('ALTER TABLE movement_logs DROP FOREIGN KEY `', @constraint_name, '`');
PREPARE stmt FROM @drop_sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- 3. Recriar a constraint com ON DELETE SET NULL
ALTER TABLE movement_logs
ADD CONSTRAINT fk_movement_logs_product
FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE SET NULL;

-- Verificar resultado
SHOW CREATE TABLE movement_logs;
