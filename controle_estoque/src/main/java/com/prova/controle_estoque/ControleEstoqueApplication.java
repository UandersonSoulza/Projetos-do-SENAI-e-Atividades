package com.prova.controle_estoque;

import javax.swing.SwingUtilities;

import com.prova.controle_estoque.dao.ConexaoBancoDados;
import com.prova.controle_estoque.view.TelaLogin;

public class ControleEstoqueApplication {

    public static void main(String[] args) {
        try {
            ConexaoBancoDados.initializeDatabase();
            SwingUtilities.invokeLater(TelaLogin::new);
        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(null,
                    "Erro ao iniciar o sistema: " + e.getMessage(),
                    "Erro", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }
}
