package com.prova.controle_estoque.dao;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class ConexaoBancoDados {
    private static final String PROPERTIES_FILE = "application.properties";

    public static Connection getConnection() throws Exception {
        Properties properties = loadProperties();
        String url = properties.getProperty("spring.datasource.url");
        String user = properties.getProperty("spring.datasource.username");
        String password = properties.getProperty("spring.datasource.password", "");
        return DriverManager.getConnection(url, user, password);
    }

    private static Properties loadProperties() throws Exception {
        Properties properties = new Properties();
        try (InputStream inputStream = ConexaoBancoDados.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
            if (inputStream == null) {
                throw new IllegalStateException("Arquivo application.properties não encontrado em classpath");
            }
            properties.load(inputStream);
        }
        return properties;
    }

    public static void initializeDatabase() throws Exception {
        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS users ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "login VARCHAR(100) UNIQUE NOT NULL, "
                    + "password VARCHAR(255) NOT NULL"
                    + ")");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS categories ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "name VARCHAR(100) UNIQUE NOT NULL"
                    + ")");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS products ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "name VARCHAR(150) NOT NULL, "
                    + "quantity INT NOT NULL, "
                    + "price DOUBLE NOT NULL, "
                    + "user_id INT NOT NULL, "
                    + "category_id INT NULL, "
                    + "FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE, "
                    + "FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE SET NULL"
                    + ")");
                    statement.executeUpdate("CREATE TABLE IF NOT EXISTS movement_logs ("
                        + "id INT AUTO_INCREMENT PRIMARY KEY, "
                        + "product_id INT NULL, "
                        + "product_name VARCHAR(150) NOT NULL, "
                        + "action VARCHAR(50) NOT NULL, "
                        + "quantity_changed INT NOT NULL, "
                        + "user_login VARCHAR(100) NOT NULL, "
                        + "timestamp DATETIME NOT NULL, "
                        + "FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE SET NULL"
                        + ")");

            // CREATE TABLE IF NOT EXISTS não altera tabelas que já existiam com uma
            // definição antiga. Se a tabela movement_logs já existia (por ex. criada
            // antes com ON DELETE CASCADE em product_id), os comandos acima são
            // ignorados pelo MySQL e a constraint antiga continua valendo. Isso fazia
            // com que o log de DELETE, inserido imediatamente antes da exclusão do
            // produto, fosse apagado em cascata junto com o produto. O bloco abaixo
            // detecta e corrige esse cenário automaticamente.
            fixMovementLogsForeignKey(connection);

            // Se a tabela products já existia de uma versão anterior (sem a coluna
            // category_id), o CREATE TABLE IF NOT EXISTS acima também é ignorado.
            // O bloco abaixo adiciona a coluna/constraint em instalações antigas.
            ensureProductsCategoryColumn(connection);

            seedDefaultCategories(connection);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inicializar o banco de dados", e);
        }
    }

    private static void ensureProductsCategoryColumn(Connection connection) throws SQLException {
        String checkQuery = "SELECT COUNT(*) AS total FROM information_schema.COLUMNS "
                + "WHERE TABLE_SCHEMA = DATABASE() "
                + "AND TABLE_NAME = 'products' "
                + "AND COLUMN_NAME = 'category_id'";
        boolean columnExists = false;
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(checkQuery)) {
            if (resultSet.next()) {
                columnExists = resultSet.getInt("total") > 0;
            }
        }

        if (!columnExists) {
            System.out.println("[DEBUG] Coluna products.category_id não existia (banco de uma versão antiga). Adicionando...");
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate("ALTER TABLE products ADD COLUMN category_id INT NULL");
                statement.executeUpdate("ALTER TABLE products ADD CONSTRAINT fk_products_category "
                        + "FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE SET NULL");
                System.out.println("[DEBUG] Coluna products.category_id adicionada com sucesso.");
            }
        }
    }

    private static void seedDefaultCategories(Connection connection) throws SQLException {
        String[] defaultCategories = {"Eletrônico", "Alimento", "Vestuário", "Limpeza", "Outros"};
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO categories (name) VALUES (?) ON DUPLICATE KEY UPDATE name = name")) {
            for (String name : defaultCategories) {
                statement.setString(1, name);
                statement.executeUpdate();
            }
        }
    }

    private static void fixMovementLogsForeignKey(Connection connection) throws SQLException {
        String constraintName = null;
        String deleteRule = null;
        String checkQuery = "SELECT CONSTRAINT_NAME, DELETE_RULE "
                + "FROM information_schema.REFERENTIAL_CONSTRAINTS "
                + "WHERE CONSTRAINT_SCHEMA = DATABASE() "
                + "AND TABLE_NAME = 'movement_logs' "
                + "AND REFERENCED_TABLE_NAME = 'products'";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(checkQuery)) {
            if (resultSet.next()) {
                constraintName = resultSet.getString("CONSTRAINT_NAME");
                deleteRule = resultSet.getString("DELETE_RULE");
            }
        }

        if (constraintName == null) {
            // Sem FK encontrada (instalação nova já criada corretamente acima, ou
            // tabela ainda sem nenhuma constraint) - nada a corrigir aqui.
            return;
        }

        if (!"SET NULL".equalsIgnoreCase(deleteRule)) {
            System.out.println("[DEBUG] Constraint de movement_logs.product_id estava como '"
                    + deleteRule + "' (provavelmente CASCADE de uma versão antiga do banco). "
                    + "Corrigindo para SET NULL para preservar logs de DELETE...");
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate("ALTER TABLE movement_logs MODIFY COLUMN product_id INT NULL");
                statement.executeUpdate("ALTER TABLE movement_logs DROP FOREIGN KEY `" + constraintName + "`");
                statement.executeUpdate("ALTER TABLE movement_logs ADD CONSTRAINT fk_movement_logs_product "
                        + "FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE SET NULL");
                System.out.println("[DEBUG] Constraint de movement_logs.product_id corrigida com sucesso.");
            }
        }
    }
}
