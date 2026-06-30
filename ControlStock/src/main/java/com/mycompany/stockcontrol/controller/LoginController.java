package com.mycompany.stockcontrol.controller;

import com.mycompany.stockcontrol.dao.UsuarioDAO;
import com.mycompany.stockcontrol.model.Usuario;
import com.mycompany.stockcontrol.util.SessaoUsuario;

public class LoginController {

    private UsuarioDAO usuarioDAO;

    public LoginController() {
        // Inicializa o DAO quando o Controller for criado
        this.usuarioDAO = new UsuarioDAO();
    }

    // Método que será chamado pelo botão "Entrar" da Tela de Login
    public boolean fazerLogin(String login, String senha) {
        // Pede ao DAO para buscar o usuário no banco
        Usuario usuario = usuarioDAO.autenticar(login, senha);
        
        if (usuario != null) {
            // Se encontrou, salva na sessão global e retorna sucesso
            SessaoUsuario.setUsuarioLogado(usuario);
            return true;
        }
        
        // Se não encontrou, retorna falso
        return false;
    }

    // Método que será chamado pelo botão "Cadastrar" da Tela de Cadastro
    public boolean cadastrarNovoUsuario(String login, String senha) {
        // Validação básica para não salvar campos vazios
        if (login == null || login.trim().isEmpty() || senha == null || senha.trim().isEmpty()) {
            return false;
        }

        // Cria o objeto e repassa para o DAO salvar
        Usuario novoUsuario = new Usuario();
        novoUsuario.setLogin(login);
        novoUsuario.setSenha(senha);

        return usuarioDAO.cadastrar(novoUsuario);
    }
    
    // Método para fazer logout (Sair do sistema)
    public void fazerLogout() {
        SessaoUsuario.limparSessao();
    }
}