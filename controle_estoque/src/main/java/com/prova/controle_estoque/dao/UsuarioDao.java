package com.prova.controle_estoque.dao;

import com.prova.controle_estoque.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

public class UsuarioDao {

    public boolean existsByLogin(String login) {
        try (Connection connection = ConexaoBancoDados.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT id FROM users WHERE login = ?")) {
            statement.setString(1, login.trim());
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao verificar login", e);
        }
    }

    public boolean save(Usuario usuario) {
        try (Connection connection = ConexaoBancoDados.getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO users (login, password) VALUES (?, ?)", PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, usuario.getLogin().trim());
            statement.setString(2, usuario.getPassword());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                return false;
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    usuario.setId(generatedKeys.getInt(1));
                }
            }
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar usuário", e);
        }
    }

    public Optional<Usuario> authenticate(String login, String password) {
        try (Connection connection = ConexaoBancoDados.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT id, login, password FROM users WHERE login = ? AND password = ?")) {
            statement.setString(1, login.trim());
            statement.setString(2, password);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setId(resultSet.getInt("id"));
                    usuario.setLogin(resultSet.getString("login"));
                    usuario.setPassword(resultSet.getString("password"));
                    return Optional.of(usuario);
                }
                return Optional.empty();
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao autenticar usuário", e);
        }
    }
}
