package com.mycompany.stockcontrol.controller;

import com.mycompany.stockcontrol.dao.CategoriaDAO;
import com.mycompany.stockcontrol.model.Categoria;

public class CategoriaController {

    private CategoriaDAO categoriaDAO;

    public CategoriaController() {
        this.categoriaDAO = new CategoriaDAO();
    }

    public boolean cadastrarCategoria(String nomeCategoria) {
        if (nomeCategoria == null || nomeCategoria.trim().isEmpty()) {
            return false;
        }

        Categoria categoria = new Categoria();
        categoria.setNomeCategoria(nomeCategoria); 

        return categoriaDAO.cadastrar(categoria);
    }

    public java.util.List<Categoria> listarCategorias() {
        return categoriaDAO.listarTodas();
    }
}