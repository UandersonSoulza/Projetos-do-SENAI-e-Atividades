package com.mycompany.stockcontrol.controller;

import com.mycompany.stockcontrol.dao.ProdutoDAO;
import com.mycompany.stockcontrol.model.Produto;
import com.mycompany.stockcontrol.model.Usuario;
import com.mycompany.stockcontrol.util.SessaoUsuario;
import java.util.ArrayList;
import java.util.List;
import com.mycompany.stockcontrol.model.Categoria;

public class ProdutoController {

    private ProdutoDAO produtoDAO;

    public ProdutoController() {
        this.produtoDAO = new ProdutoDAO();
    }

    // Requisito 3: Cadastrar produto associando ao usuário logado
    // 👇 Adicionamos o 'int idCategoria' aqui nos parênteses
    public boolean cadastrarProduto(String nome, int quantidade, double preco, int idCategoria) {
        if (nome == null || nome.trim().isEmpty() || quantidade < 0 || preco < 0 || !SessaoUsuario.isLogado()) {
            return false;
        }
        
        Produto produto = new Produto();
        produto.setNome(nome);
        produto.setQuantidade(quantidade);
        produto.setPreco(preco);
        produto.setUsuario(SessaoUsuario.getUsuarioLogado());
        
        // 👇 CRIAMOS E COLOCAMOS A CATEGORIA NO PRODUTO PARA CORRIGIR O ERRO
        Categoria categoria = new Categoria();
        categoria.setIdCategorias(idCategoria);
        produto.setCategoria(categoria);
        
        return produtoDAO.cadastrar(produto);
    }

    // Requisito 4: Listar todos os produtos
    public List<Produto> listarProdutos() {
        return produtoDAO.listarTodos();
    }

    // Requisito 5: Buscar produtos pelo nome
    public List<Produto> buscarProdutosPorNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            return produtoDAO.listarTodos(); // Se a busca for vazia, traz tudo
        }
        return produtoDAO.buscarPorNome(nome);
    }

    // Requisito 5: Atualizar produto existente
public boolean atualizarProduto(int id, String nome, int quantidade, double preco, int idCategoria) {
    Produto produto = new Produto();
    produto.setIdProduto(id);
    produto.setNome(nome);
    produto.setQuantidade(quantidade);
    produto.setPreco(preco);
    
    Categoria cat = new Categoria();
    cat.setIdCategorias(idCategoria);
    produto.setCategoria(cat); // Associa a categoria atualizada antes de mandar para o DAO
    
    return produtoDAO.atualizar(produto);
}

    // Requisito 5: Excluir produto do sistema
    public boolean excluirProduto(int idProduto) {
        if (idProduto <= 0) {
            return false;
        }
        return produtoDAO.excluir(idProduto);
    }
}