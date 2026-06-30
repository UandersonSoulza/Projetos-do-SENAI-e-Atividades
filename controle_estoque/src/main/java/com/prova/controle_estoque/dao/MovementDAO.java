package com.prova.controle_estoque.dao;

import com.prova.controle_estoque.model.Movement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MovementDAO {

    public List<Movement> getAllMovements() {
        List<Movement> movements = new ArrayList<>();
        String query = "SELECT p.id, p.name, p.quantity, p.price, (p.quantity * p.price) AS total_value, u.login " +
                       "FROM products p " +
                       "JOIN users u ON p.user_id = u.id " +
                       "ORDER BY p.id";
        try (Connection connection = ConexaoBancoDados.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Movement movement = new Movement();
                movement.setProductId(resultSet.getInt("id"));
                movement.setProductName(resultSet.getString("name"));
                movement.setQuantity(resultSet.getInt("quantity"));
                movement.setPrice(resultSet.getDouble("price"));
                movement.setTotalValue(resultSet.getDouble("total_value"));
                movement.setUserLogin(resultSet.getString("login"));
                movements.add(movement);
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar movimentações", e);
        }
        return movements;
    }

    public List<Movement> getMovementsByUser(String userLogin) {
        List<Movement> movements = new ArrayList<>();
        String query = "SELECT p.id, p.name, p.quantity, p.price, (p.quantity * p.price) AS total_value, u.login " +
                       "FROM products p " +
                       "JOIN users u ON p.user_id = u.id " +
                       "WHERE u.login = ? " +
                       "ORDER BY p.id";
        try (Connection connection = ConexaoBancoDados.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, userLogin);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Movement movement = new Movement();
                    movement.setProductId(resultSet.getInt("id"));
                    movement.setProductName(resultSet.getString("name"));
                    movement.setQuantity(resultSet.getInt("quantity"));
                    movement.setPrice(resultSet.getDouble("price"));
                    movement.setTotalValue(resultSet.getDouble("total_value"));
                    movement.setUserLogin(resultSet.getString("login"));
                    movements.add(movement);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar movimentações do usuário", e);
        }
        return movements;
    }

    public double getTotalInventoryValue() {
        String query = "SELECT SUM(p.quantity * p.price) AS total FROM products p";
        try (Connection connection = ConexaoBancoDados.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getDouble("total");
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao calcular valor total do estoque", e);
        }
        return 0.0;
    }
}
