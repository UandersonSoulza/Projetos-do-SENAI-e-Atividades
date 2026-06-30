-- Schema do banco de movimentação para MySQL

CREATE DATABASE IF NOT EXISTS controle_estoque;
USE controle_estoque;

CREATE TABLE IF NOT EXISTS users (
  id INT AUTO_INCREMENT PRIMARY KEY,
  login VARCHAR(100) UNIQUE NOT NULL,
  password VARCHAR(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS categories (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) UNIQUE NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS products (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(150) NOT NULL,
  quantity INT NOT NULL,
  price DOUBLE NOT NULL,
  user_id INT NOT NULL,
  category_id INT NULL,
  FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
  FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS movement_logs (
  id INT AUTO_INCREMENT PRIMARY KEY,
  product_id INT NULL,
  product_name VARCHAR(150) NOT NULL,
  action VARCHAR(50) NOT NULL,
  quantity_changed INT NOT NULL,
  user_login VARCHAR(100) NOT NULL,
  timestamp DATETIME NOT NULL,
  FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Categorias padrão (a aplicação também insere estas automaticamente ao iniciar)
INSERT INTO categories (name) VALUES ('Eletrônico'), ('Alimento'), ('Vestuário'), ('Limpeza'), ('Outros')
  ON DUPLICATE KEY UPDATE name = name;

-- Exemplos de inserção (opcionais)
INSERT INTO users (login, password) VALUES ('admin', 'admin');
INSERT INTO products (name, quantity, price, user_id, category_id) VALUES ('Produto A', 10, 9.99, 1, 1);
INSERT INTO movement_logs (product_id, product_name, action, quantity_changed, user_login, timestamp) VALUES (1, 'Produto A', 'INSERT', 10, 'admin', NOW());
