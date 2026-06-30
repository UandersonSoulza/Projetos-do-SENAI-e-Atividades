package com.mycompany.stockcontrol.dao;

import com.mycompany.stockcontrol.connection.ConnectionFactory;
import com.mycompany.stockcontrol.model.Categoria;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {

    // MÉTODO 1: CADASTRAR
    public boolean cadastrar(Categoria categoria) {
        String sql = "INSERT INTO categorias (nome_categoria) VALUES (?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, categoria.getNomeCategoria());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar categoria: " + e.getMessage());
            return false;
        }
    }

    // MÉTODO 2: LISTAR TODAS
    public List<Categoria> listarTodas() {
        String sql = "SELECT * FROM categorias";
        List<Categoria> lista = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Categoria cat = new Categoria();
                cat.setIdCategorias(rs.getInt("idcategorias")); 
                cat.setNomeCategoria(rs.getString("nome_categoria"));
                lista.add(cat);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar categorias: " + e.getMessage());
        }
        return lista;
    }
}