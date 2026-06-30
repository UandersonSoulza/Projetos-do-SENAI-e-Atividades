package com.mycompany.stockcontrol.dao;

import com.mycompany.stockcontrol.connection.ConnectionFactory;
import com.mycompany.stockcontrol.model.Movimentacao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MovimentacaoDAO {

    public boolean registrar(Movimentacao mov) {
        String sqlMov = "INSERT INTO movimentacoes (usuarios_idusuarios, produtos_idprodutos, quantidade_movimentacao, tipo_movimentacao) VALUES (?, ?, ?, ?)";
        
        // SQL para atualizar a quantidade na tabela produtos
        String sqlProd = "";
        if (mov.getTipo().equals("ENTRADA")) {
            sqlProd = "UPDATE produtos SET quantidade_produto = quantidade_produto + ? WHERE idprodutos = ?";
        } else {
            sqlProd = "UPDATE produtos SET quantidade_produto = quantidade_produto - ? WHERE idprodutos = ?";
        }

        try (Connection conn = ConnectionFactory.getConnection()) {
            conn.setAutoCommit(false); // Ativa transação para garantir que ambas as operações deem certo

            // 1. Insere o registro de movimentação
            try (PreparedStatement stmtMov = conn.prepareStatement(sqlMov)) {
                stmtMov.setInt(1, mov.getIdUsuario());
                stmtMov.setInt(2, mov.getIdProduto());
                stmtMov.setInt(3, mov.getQuantidade());
                stmtMov.setString(4, mov.getTipo());
                stmtMov.executeUpdate();
            }

            // 2. Atualiza o saldo do produto em estoque
            try (PreparedStatement stmtProd = conn.prepareStatement(sqlProd)) {
                stmtProd.setInt(1, mov.getQuantidade());
                stmtProd.setInt(2, mov.getIdProduto());
                stmtProd.executeUpdate();
            }

            conn.commit(); // Salva as alterações no MySQL
            return true;

        } catch (SQLException e) {
            System.err.println("Erro na movimentação: " + e.getMessage());
            return false;
        }
    }
}