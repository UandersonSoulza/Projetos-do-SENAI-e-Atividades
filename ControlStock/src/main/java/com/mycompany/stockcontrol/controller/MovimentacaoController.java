package com.mycompany.stockcontrol.controller;

import com.mycompany.stockcontrol.dao.MovimentacaoDAO;
import com.mycompany.stockcontrol.model.Movimentacao;
import com.mycompany.stockcontrol.util.SessaoUsuario;

public class MovimentacaoController {
    private MovimentacaoDAO movimentacaoDAO;

    public MovimentacaoController() {
        this.movimentacaoDAO = new MovimentacaoDAO();
    }

    public boolean salvarMovimentacao(int idProduto, int quantidade, String tipo) {
        // Validações básicas
        if (idProduto <= 0 || quantidade <= 0 || tipo == null) {
            return false;
        }

        // Verifica o login do usuário (Requisito de segurança)
        if (!SessaoUsuario.isLogado()) {
            return false;
        }

        Movimentacao mov = new Movimentacao();
        mov.setIdProduto(idProduto);
        mov.setQuantidade(quantidade);
        mov.setTipo(tipo);
        
        // Pega o ID do usuário diretamente da sessão ativa usando o nome correto do Getter
        mov.setIdUsuario(SessaoUsuario.getUsuarioLogado().getIdUsuario()); 

        return movimentacaoDAO.registrar(mov);
    }
}