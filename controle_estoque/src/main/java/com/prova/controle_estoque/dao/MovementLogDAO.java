package com.prova.controle_estoque.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.prova.controle_estoque.model.MovementLog;

public class MovementLogDAO {

    public boolean save(MovementLog log) {
        try (Connection connection = ConexaoBancoDados.getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO movement_logs (product_id, product_name, action, quantity_changed, user_login, timestamp) VALUES (?, ?, ?, ?, ?, ?)") ) {
            if (log.getProductId() != null) {
                statement.setInt(1, log.getProductId());
            } else {
                statement.setNull(1, java.sql.Types.INTEGER);
            }
            statement.setString(2, log.getProductName());
            statement.setString(3, log.getAction());
            statement.setInt(4, log.getQuantityChanged());
            statement.setString(5, log.getUserLogin());
            statement.setTimestamp(6, Timestamp.valueOf(log.getTimestamp()));
            
            int result = statement.executeUpdate();
            System.out.println("[DEBUG] MovementLogDAO.save() - Inserção " + (result > 0 ? "sucesso" : "falha") + 
                    ". Produto=" + log.getProductName() + ", Ação=" + log.getAction() + ", Qty=" + log.getQuantityChanged());
            return result > 0;
        } catch (Exception e) {
            System.err.println("[DEBUG] ERRO em MovementLogDAO.save(): " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erro ao salvar log de movimentação", e);
        }
    }

    public List<MovementLog> getAllLogs() {
        List<MovementLog> logs = new ArrayList<>();
        String query = "SELECT id, product_id, product_name, action, quantity_changed, user_login, timestamp " +
                       "FROM movement_logs ORDER BY timestamp DESC";
        try (Connection connection = ConexaoBancoDados.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            int count = 0;
            while (resultSet.next()) {
                count++;
                MovementLog log = new MovementLog();
                log.setId(resultSet.getInt("id"));
                log.setProductId(resultSet.getInt("product_id"));
                log.setProductName(resultSet.getString("product_name"));
                log.setAction(resultSet.getString("action"));
                log.setQuantityChanged(resultSet.getInt("quantity_changed"));
                log.setUserLogin(resultSet.getString("user_login"));
                log.setTimestamp(resultSet.getTimestamp("timestamp").toLocalDateTime());
                logs.add(log);
                System.out.println("[DEBUG] getAllLogs - Lido log #" + count + ": " + log.getProductName() + " (" + log.getAction() + ")");
            }
            System.out.println("[DEBUG] getAllLogs - Total de logs lidos: " + count);
        } catch (Exception e) {
            System.err.println("[DEBUG] ERRO em getAllLogs: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar logs de movimentação", e);
        }
        return logs;
    }

    public List<MovementLog> getLogsByUser(String userLogin) {
        List<MovementLog> logs = new ArrayList<>();
        String query = "SELECT id, product_id, product_name, action, quantity_changed, user_login, timestamp " +
                       "FROM movement_logs WHERE user_login = ? ORDER BY timestamp DESC";
        try (Connection connection = ConexaoBancoDados.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, userLogin);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    MovementLog log = new MovementLog();
                    log.setId(resultSet.getInt("id"));
                    log.setProductId(resultSet.getInt("product_id"));
                    log.setProductName(resultSet.getString("product_name"));
                    log.setAction(resultSet.getString("action"));
                    log.setQuantityChanged(resultSet.getInt("quantity_changed"));
                    log.setUserLogin(resultSet.getString("user_login"));
                    log.setTimestamp(resultSet.getTimestamp("timestamp").toLocalDateTime());
                    logs.add(log);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar logs de movimentação do usuário", e);
        }
        return logs;
    }
}
