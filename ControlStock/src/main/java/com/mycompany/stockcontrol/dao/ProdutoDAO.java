package com.mycompany.stockcontrol.dao;

import com.mycompany.stockcontrol.connection.ConnectionFactory;
import com.mycompany.stockcontrol.model.Produto;
import com.mycompany.stockcontrol.model.Usuario;
import com.mycompany.stockcontrol.model.Categoria;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    public boolean cadastrar(Produto produto) {
        String sql = "INSERT INTO produtos (nome_produtos, quantidade_produto, preco, usuarios_idusuarios, categorias_idcategorias) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, produto.getNome());
            stmt.setInt(2, produto.getQuantidade());
            stmt.setDouble(3, produto.getPreco());
            stmt.setInt(4, produto.getUsuario().getIdUsuario());
            stmt.setInt(5, produto.getCategoria().getIdCategorias());
            
            stmt.executeUpdate();
            return true;
            
        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar produto: " + e.getMessage());
            return false;
        }
    }

    public List<Produto> listarTodos() {
        String sql = "SELECT p.*, u.login, c.nome_categoria " +
                     "FROM produtos p " +
                     "INNER JOIN usuarios u ON p.usuarios_idusuarios = u.idusuarios " +
                     "INNER JOIN categorias c ON p.categorias_idcategorias = c.idcategorias";
        return executarBusca(sql, null);
    }

    public List<Produto> buscarPorNome(String nomeBusca) {
        String sql = "SELECT p.*, u.login, c.nome_categoria " +
                     "FROM produtos p " +
                     "INNER JOIN usuarios u ON p.usuarios_idusuarios = u.idusuarios " +
                     "INNER JOIN categorias c ON p.categorias_idcategorias = c.idcategorias " +
                     "WHERE p.nome_produtos LIKE ?";
        return executarBusca(sql, "%" + nomeBusca + "%");
    }

    private List<Produto> executarBusca(String sql, String parametroBusca) {
        List<Produto> listaProdutos = new ArrayList<>();
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            if (parametroBusca != null) {
                stmt.setString(1, parametroBusca);
            }
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Produto p = new Produto();
                    p.setIdProduto(rs.getInt("idprodutos")); 
                    p.setNome(rs.getString("nome_produtos")); 
                    p.setQuantidade(rs.getInt("quantidade_produto"));
                    p.setPreco(rs.getDouble("preco")); 
                   
                    Categoria cat = new Categoria();
                    cat.setIdCategorias(rs.getInt("categorias_idcategorias")); 
                    cat.setNomeCategoria(rs.getString("nome_categoria")); 
                    p.setCategoria(cat);
                    
                    Usuario u = new Usuario();
                    u.setIdUsuario(rs.getInt("usuarios_idusuarios"));
                    u.setLogin(rs.getString("login")); 
                    p.setUsuario(u);

                    listaProdutos.add(p);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar produtos: " + e.getMessage());
        }
        
        return listaProdutos;
    }

    public boolean atualizar(Produto produto) {
        // CORRIGIDO: Adicionado "categorias_idcategorias = ?" para alterar a categoria no banco de dados
        String sql = "UPDATE produtos SET nome_produtos = ?, quantidade_produto = ?, preco = ?, categorias_idcategorias = ? WHERE idprodutos = ?";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, produto.getNome());
            stmt.setInt(2, produto.getQuantidade());
            stmt.setDouble(3, produto.getPreco());
            // 👇 NOVA LINHA: Passa o ID da nova categoria selecionada
            stmt.setInt(4, produto.getCategoria().getIdCategorias());
            // 👇 ALTERADO: O ID do produto mudou para a posição 5
            stmt.setInt(5, produto.getIdProduto());
            
            stmt.executeUpdate();
            return true;
            
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar produto: " + e.getMessage());
            return false;
        }
    }

    public boolean excluir(int idProduto) {
        String sql = "DELETE FROM produtos WHERE idprodutos = ?";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, idProduto);
            stmt.executeUpdate();
            return true;
            
        } catch (SQLException e) {
            System.err.println("Erro ao excluir produto: " + e.getMessage());
            return false;
        }
    }
}