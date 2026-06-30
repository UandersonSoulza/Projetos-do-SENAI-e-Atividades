package com.prova.controle_estoque.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.prova.controle_estoque.model.Categoria;

public class CategoriaDao {

    public List<Categoria> findAll() {
        List<Categoria> categorias = new ArrayList<>();
        String query = "SELECT id, name FROM categories ORDER BY name";
        try (Connection connection = ConexaoBancoDados.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                categorias.add(new Categoria(resultSet.getInt("id"), resultSet.getString("name")));
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar categorias", e);
        }
        return categorias;
    }

    public Optional<Categoria> findById(int id) {
        String query = "SELECT id, name FROM categories WHERE id = ?";
        try (Connection connection = ConexaoBancoDados.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(new Categoria(resultSet.getInt("id"), resultSet.getString("name")));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar categoria", e);
        }
        return Optional.empty();
    }

    public boolean save(Categoria categoria) {
        try (Connection connection = ConexaoBancoDados.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO categories (name) VALUES (?)", PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, categoria.getName().trim());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                return false;
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    categoria.setId(generatedKeys.getInt(1));
                }
            }
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar categoria", e);
        }
    }

    public boolean existsByName(String name) {
        String query = "SELECT id FROM categories WHERE name = ?";
        try (Connection connection = ConexaoBancoDados.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name.trim());
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao verificar categoria", e);
        }
    }

    public boolean deleteById(int id) {
        // Produtos que usam essa categoria ficam com category_id = NULL
        // automaticamente (FK ON DELETE SET NULL), não são excluídos.
        try (Connection connection = ConexaoBancoDados.getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM categories WHERE id = ?")) {
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao excluir categoria", e);
        }
    }
}
