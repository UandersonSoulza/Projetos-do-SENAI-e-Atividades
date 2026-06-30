package com.prova.controle_estoque.view;

import com.prova.controle_estoque.controller.ControladorAutenticacao;

import javax.swing.*;
import java.awt.*;

public class TelaCadastro extends JFrame {
    private final ControladorAutenticacao authController = new ControladorAutenticacao();
    private final JTextField loginField;
    private final JPasswordField passwordField;

    public TelaCadastro() {
        setTitle("Controle de Estoque - Cadastro de usuário");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(380, 240);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel contentPanel = new JPanel(new GridLayout(4, 1, 8, 8));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        loginField = new JTextField();
        passwordField = new JPasswordField();

        contentPanel.add(createLabeledPanel("Login:", loginField));
        contentPanel.add(createLabeledPanel("Senha:", passwordField));

        JButton registerButton = new JButton("Cadastrar");
        registerButton.addActionListener(event -> handleRegister());

        contentPanel.add(new JLabel(""));
        contentPanel.add(registerButton);

        add(contentPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    private JPanel createLabeledPanel(String labelText, JComponent field) {
        JPanel panel = new JPanel(new BorderLayout(4, 4));
        panel.add(new JLabel(labelText), BorderLayout.WEST);
        panel.add(field, BorderLayout.CENTER);
        return panel;
    }

    private void handleRegister() {
        String login = loginField.getText();
        String password = new String(passwordField.getPassword());
        String error = authController.register(login, password);
        if (error != null) {
            JOptionPane.showMessageDialog(this, error, "Falha no cadastro", JOptionPane.WARNING_MESSAGE);
            return;
        }
        JOptionPane.showMessageDialog(this, "Usuário cadastrado com sucesso!", "Cadastro concluído", JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }
}
