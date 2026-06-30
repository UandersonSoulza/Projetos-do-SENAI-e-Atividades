package com.prova.controle_estoque.controller;

import com.prova.controle_estoque.dao.CategoriaDao;
import com.prova.controle_estoque.dao.ProdutoDao;
import com.prova.controle_estoque.model.Categoria;
import com.prova.controle_estoque.model.Produto;
import com.prova.controle_estoque.model.Usuario;

import java.util.List;
import java.util.Optional;

public class ControladorProduto {
    private final ProdutoDao produtoDao = new ProdutoDao();
    private final CategoriaDao categoriaDao = new CategoriaDao();
    private final Usuario currentUser;

    public ControladorProduto(Usuario currentUser) {
        this.currentUser = currentUser;
    }

    public List<Produto> listAll() {
        return produtoDao.findAll();
    }

    public List<Categoria> listCategories() {
        return categoriaDao.findAll();
    }

    public List<Produto> searchByName(String name) {
        if (name == null || name.isBlank()) {
            return listAll();
        }
        return produtoDao.findByName(name.trim());
    }

    public Optional<String> addProduct(String name, String quantityText, String priceText, Integer categoryId) {
        if (name == null || name.isBlank()) {
            return Optional.of("Nome do produto é obrigatório.");
        }
        int quantity;
        double price;
        try {
            quantity = Integer.parseInt(quantityText.trim());
            if (quantity < 0) {
                return Optional.of("Quantidade deve ser um número inteiro não negativo.");
            }
        } catch (NumberFormatException e) {
            return Optional.of("Quantidade deve ser um número inteiro válido.");
        }
        try {
            price = Double.parseDouble(priceText.trim());
            if (price < 0) {
                return Optional.of("Preço deve ser um número não negativo.");
            }
        } catch (NumberFormatException e) {
            return Optional.of("Preço deve ser um número válido.");
        }
        Produto produto = new Produto();
        produto.setName(name.trim());
        produto.setQuantity(quantity);
        produto.setPrice(price);
        produto.setUserId(currentUser.getId());
        produto.setCategoryId(categoryId);
        boolean saved = produtoDao.save(produto, currentUser.getLogin());
        return saved ? Optional.empty() : Optional.of("Erro ao cadastrar produto.");
    }

    public Optional<String> updateProduct(int productId, String name, String quantityText, String priceText, Integer categoryId) {
        if (name == null || name.isBlank()) {
            return Optional.of("Nome do produto é obrigatório.");
        }
        Produto existing = produtoDao.findById(productId).orElse(null);
        if (existing == null) {
            return Optional.of("Produto não encontrado.");
        }
        int quantity;
        double price;
        try {
            quantity = Integer.parseInt(quantityText.trim());
            if (quantity < 0) {
                return Optional.of("Quantidade deve ser um número inteiro não negativo.");
            }
        } catch (NumberFormatException e) {
            return Optional.of("Quantidade deve ser um número inteiro válido.");
        }
        try {
            price = Double.parseDouble(priceText.trim());
            if (price < 0) {
                return Optional.of("Preço deve ser um número não negativo.");
            }
        } catch (NumberFormatException e) {
            return Optional.of("Preço deve ser um número válido.");
        }
        existing.setName(name.trim());
        existing.setQuantity(quantity);
        existing.setPrice(price);
        existing.setCategoryId(categoryId);
        boolean updated = produtoDao.update(existing, currentUser.getLogin());
        return updated ? Optional.empty() : Optional.of("Erro ao atualizar produto.");
    }

    public boolean deleteProduct(int productId) {
        return produtoDao.delete(productId, currentUser.getLogin());
    }

    public Produto getProductById(int productId) {
        return produtoDao.findById(productId).orElse(null);
    }

    public Usuario getCurrentUser() {
        return currentUser;
    }
}
