package com.prova.controle_estoque.controller;

import com.prova.controle_estoque.dao.CategoriaDao;
import com.prova.controle_estoque.model.Categoria;

import java.util.List;
import java.util.Optional;

public class ControladorCategoria {
    private final CategoriaDao categoriaDao = new CategoriaDao();

    public List<Categoria> listAll() {
        return categoriaDao.findAll();
    }

    public Optional<String> addCategory(String name) {
        if (name == null || name.isBlank()) {
            return Optional.of("Nome da categoria é obrigatório.");
        }
        String trimmed = name.trim();
        if (categoriaDao.existsByName(trimmed)) {
            return Optional.of("Já existe uma categoria com esse nome.");
        }
        Categoria categoria = new Categoria();
        categoria.setName(trimmed);
        boolean saved = categoriaDao.save(categoria);
        return saved ? Optional.empty() : Optional.of("Erro ao cadastrar categoria.");
    }

    public boolean deleteCategory(int id) {
        return categoriaDao.deleteById(id);
    }
}
