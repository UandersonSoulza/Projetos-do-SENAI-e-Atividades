package com.mycompany.stockcontrol.util;

import com.mycompany.stockcontrol.model.Usuario;

public class SessaoUsuario {
    
    // Variável global que guarda os dados do usuário atualmente logado
    private static Usuario usuarioLogado;

    // Retorna o usuário logado no momento
    public static Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    // Define quem é o usuário logado (usaremos isso no LoginController)
    public static void setUsuarioLogado(Usuario usuario) {
        SessaoUsuario.usuarioLogado = usuario;
    }

    // Verifica se existe alguém logado no sistema
    public static boolean isLogado() {
        return usuarioLogado != null;
    }

    // Limpa a sessão (usaremos isso para o botão de "Sair/Logout")
    public static void limparSessao() {
        usuarioLogado = null;
    }
}