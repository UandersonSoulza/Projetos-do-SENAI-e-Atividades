package com.mycompany.stockcontrol.controller;

import com.mycompany.stockcontrol.dao.UsuarioDAO;
import com.mycompany.stockcontrol.model.Usuario;

public class UsuarioController {
    
    private UsuarioDAO usuarioDAO;

    public UsuarioController() {
        this.usuarioDAO = new UsuarioDAO();
    }

    // Gerencia o cadastro de novos usuários no sistema
    public boolean cadastrarNovoUsuario(String login, String senha) {
        if (login == null || login.trim().isEmpty() || senha == null || senha.trim().isEmpty()) {
            return false;
        }

        Usuario novoUsuario = new Usuario();
        novoUsuario.setLogin(login);
        novoUsuario.setSenha(senha);

        return usuarioDAO.cadastrar(novoUsuario);
    }
}