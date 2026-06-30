package com.mycompany.controlstock;

import com.mycompany.stockcontrol.view.TelaLogin;

public class ControlStock {

    public static void main(String[] args) {
        // Altera o visual das telas para o padrão do sistema operacional (opcional, mas fica mais bonito)
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            System.err.println("Não foi possível definir o visual das telas: " + ex.getMessage());
        }

        // Cria a instância da sua Tela de Login e faz ela aparecer na tela
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                TelaLogin tela = new TelaLogin();
                tela.setLocationRelativeTo(null); // Centraliza a tela de login no meio do monitor
                tela.setVisible(true); // Exibe a tela de login
            }
        });
    }
}