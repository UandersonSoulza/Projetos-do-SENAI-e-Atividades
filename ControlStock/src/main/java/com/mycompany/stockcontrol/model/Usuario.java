package com.mycompany.stockcontrol.model;

public class Usuario {
    private int idUsuario; // ou idusuarios, mude o getter abaixo conforme sua variável
    private String login;
    private String senha;

    // Garanta que o método GET do ID esteja exatamente com este nome:
    public int getIdUsuario() {
        return idUsuario; 
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
}