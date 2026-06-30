package com.mycompany.stockcontrol.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    
    // Configurações do Banco de Dados
    // Altere "controle_estoque" para o nome do banco que você criará no MySQL
    private static final String URL = "jdbc:mysql://localhost:3306/controle_estoque"; 
    private static final String USER = "root"; // Seu usuário do MySQL (padrão é root)
    private static final String PASS = "root"; // Sua senha do MySQL (padrão do XAMPP geralmente é vazio)

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar com o banco de dados: " + e.getMessage());
        }
    }
}