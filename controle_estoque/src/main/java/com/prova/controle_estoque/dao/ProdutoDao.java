package com.prova.controle_estoque.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.prova.controle_estoque.model.MovementLog;
import com.prova.controle_estoque.model.Produto;

public class ProdutoDao {

    public List<Produto> findAll() {
        return findByQuery("SELECT p.id, p.name, p.quantity, p.price, p.user_id, u.login AS user_login, "
                + "p.category_id, c.name AS category_name "
                + "FROM products p JOIN users u ON p.user_id = u.id "
                + "LEFT JOIN categories c ON p.category_id = c.id ORDER BY p.id");
    }

    public List<Produto> findByName(String name) {
        return findByQuery("SELECT p.id, p.name, p.quantity, p.price, p.user_id, u.login AS user_login, "
                + "p.category_id, c.name AS category_name "
                + "FROM products p JOIN users u ON p.user_id = u.id "
                + "LEFT JOIN categories c ON p.category_id = c.id "
                + "WHERE p.name LIKE ? ORDER BY p.id", "%" + name.trim() + "%");
    }

    public Optional<Produto> findById(int id) {
        List<Produto> results = findByQuery("SELECT p.id, p.name, p.quantity, p.price, p.user_id, u.login AS user_login, "
                + "p.category_id, c.name AS category_name "
                + "FROM products p JOIN users u ON p.user_id = u.id "
                + "LEFT JOIN categories c ON p.category_id = c.id "
                + "WHERE p.id = ? ORDER BY p.id", id);
        return results.stream().findFirst();
    }

    public boolean save(Produto produto, String userLogin) {
        try (Connection connection = ConexaoBancoDados.getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO products (name, quantity, price, user_id, category_id) VALUES (?, ?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, produto.getName().trim());
            statement.setInt(2, produto.getQuantity());
            statement.setDouble(3, produto.getPrice());
            statement.setInt(4, produto.getUserId());
            if (produto.getCategoryId() != null) {
                statement.setInt(5, produto.getCategoryId());
            } else {
                statement.setNull(5, java.sql.Types.INTEGER);
            }
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                return false;
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    produto.setId(generatedKeys.getInt(1));
                }
            }
            MovementLog log = new MovementLog(produto.getId(), produto.getName(), "INSERT", produto.getQuantity(), userLogin);
            new MovementLogDAO().save(log);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar produto", e);
        }
    }

    public boolean update(Produto produto, String userLogin) {
        try (Connection connection = ConexaoBancoDados.getConnection();
             PreparedStatement statement = connection.prepareStatement("UPDATE products SET name = ?, quantity = ?, price = ?, category_id = ? WHERE id = ?")) {
            statement.setString(1, produto.getName().trim());
            statement.setInt(2, produto.getQuantity());
            statement.setDouble(3, produto.getPrice());
            if (produto.getCategoryId() != null) {
                statement.setInt(4, produto.getCategoryId());
            } else {
                statement.setNull(4, java.sql.Types.INTEGER);
            }
            statement.setInt(5, produto.getId());
            boolean result = statement.executeUpdate() > 0;
            if (result) {
                MovementLog log = new MovementLog(produto.getId(), produto.getName(), "UPDATE", produto.getQuantity(), userLogin);
                new MovementLogDAO().save(log);
            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar produto", e);
        }
    }

    public boolean delete(int id, String userLogin) {
        Optional<Produto> produto = findById(id);
        if (produto.isEmpty()) {
            return false;
        }
        Produto p = produto.get();
        // salvar log antes da exclusão para evitar constraints
        try {
            System.out.println("[DEBUG] Salvando log de DELETE para produto: " + p.getName());
            MovementLog log = new MovementLog(p.getId(), p.getName(), "DELETE", p.getQuantity(), userLogin);
            new MovementLogDAO().save(log);
            System.out.println("[DEBUG] Log de DELETE salvo com sucesso");
        } catch (Exception ex) {
            System.err.println("[DEBUG] ERRO ao salvar log de DELETE: " + ex.getMessage());
            ex.printStackTrace();
        }
        try (Connection connection = ConexaoBancoDados.getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM products WHERE id = ?")) {
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao excluir produto", e);
        }
    }

    private List<Produto> findByQuery(String query, Object... params) {
        List<Produto> products = new ArrayList<>();
        try (Connection connection = ConexaoBancoDados.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            for (int index = 0; index < params.length; index++) {
                Object param = params[index];
                int position = index + 1;
                if (param instanceof Integer) {
                    statement.setInt(position, (Integer) param);
                } else {
                    statement.setString(position, param.toString());
                }
            }
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Produto produto = new Produto();
                    produto.setId(resultSet.getInt("id"));
                    produto.setName(resultSet.getString("name"));
                    produto.setQuantity(resultSet.getInt("quantity"));
                    produto.setPrice(resultSet.getDouble("price"));
                    produto.setUserId(resultSet.getInt("user_id"));
                    produto.setUserLogin(resultSet.getString("user_login"));
                    int categoryId = resultSet.getInt("category_id");
                    produto.setCategoryId(resultSet.wasNull() ? null : categoryId);
                    produto.setCategoryName(resultSet.getString("category_name"));
                    products.add(produto);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar produtos", e);
        }
        return products;
    }
}
