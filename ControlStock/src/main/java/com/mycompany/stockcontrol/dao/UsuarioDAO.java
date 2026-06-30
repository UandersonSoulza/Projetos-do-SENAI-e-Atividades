package com.mycompany.stockcontrol.dao;

import com.mycompany.stockcontrol.connection.ConnectionFactory;
import com.mycompany.stockcontrol.model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {

    // Método para cadastrar um novo usuário (Requisito 2)
    public boolean cadastrar(Usuario usuario) {
        String sql = "INSERT INTO usuarios (login, senha) VALUES (?, ?)";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, usuario.getLogin());
            stmt.setString(2, usuario.getSenha());
            
            stmt.executeUpdate();
            return true; // Retorna verdadeiro se inseriu com sucesso
            
        } catch (SQLException e) {
            // Se o login já existir, o banco de dados vai bloquear e cair neste erro
            System.err.println("Erro ao cadastrar usuário (login duplicado?): " + e.getMessage());
            return false;
        }
    }

    // Método para validar o login (Requisito 1)
    public Usuario autenticar(String login, String senha) {
        String sql = "SELECT * FROM usuarios WHERE login = ? AND senha = ?";
        Usuario usuarioEncontrado = null;
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, login);
            stmt.setString(2, senha);
            
            try (ResultSet rs = stmt.executeQuery()) {
                // Se encontrar uma linha no banco, preenchemos o objeto Usuário
                if (rs.next()) {
                    usuarioEncontrado = new Usuario();
                    usuarioEncontrado.setIdUsuario(rs.getInt("idusuarios"));
                    usuarioEncontrado.setLogin(rs.getString("login"));
                    usuarioEncontrado.setSenha(rs.getString("senha"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao tentar autenticar: " + e.getMessage());
        }
        
        // Retorna o usuário preenchido se o login estiver correto, ou null se estiver errado
        return usuarioEncontrado; 
    }
}