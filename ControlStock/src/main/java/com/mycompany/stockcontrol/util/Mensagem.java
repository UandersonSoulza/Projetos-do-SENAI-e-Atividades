package com.mycompany.stockcontrol.util;

import java.awt.Component;
import javax.swing.JOptionPane;

public class Mensagem {

    public static void exibirSucesso(Component parent, String mensagem) {
        JOptionPane.showMessageDialog(parent, mensagem, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void exibirErro(Component parent, String mensagem) {
        JOptionPane.showMessageDialog(parent, mensagem, "Erro", JOptionPane.ERROR_MESSAGE);
    }

    public static void exibirAviso(Component parent, String mensagem) {
        JOptionPane.showMessageDialog(parent, mensagem, "Atenção", JOptionPane.WARNING_MESSAGE);
    }
}