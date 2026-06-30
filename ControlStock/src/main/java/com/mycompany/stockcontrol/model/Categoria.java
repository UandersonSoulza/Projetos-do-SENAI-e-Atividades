package com.mycompany.stockcontrol.model;

public class Categoria {
    private int idCategorias;
    private String nomeCategoria;

    // Construtor padrão
    public Categoria() {}

    // Getters e Setters
    public int getIdCategorias() {
        return idCategorias;
    }

    public void setIdCategorias(int idCategorias) {
        this.idCategorias = idCategorias;
    }

    public String getNomeCategoria() {
        return nomeCategoria;
    }

    public void setNomeCategoria(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
    }
    
    // Diz ao ComboBox para mostrar o nome da categoria
    @Override
    public String toString() {
        return this.nomeCategoria;
    }
}