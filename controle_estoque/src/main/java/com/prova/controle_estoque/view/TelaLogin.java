package com.prova.controle_estoque.view;

import com.prova.controle_estoque.controller.ControladorAutenticacao;
import com.prova.controle_estoque.model.Usuario;

import javax.swing.*;
import java.awt.*;

public class TelaLogin extends JFrame {
    private final ControladorAutenticacao authController = new ControladorAutenticacao();
    private final JTextField loginField;
    private final JPasswordField passwordField;

    public TelaLogin() {
        setTitle("Controle de Estoque - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(360, 220);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel contentPanel = new JPanel(new GridLayout(4, 1, 8, 8));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        loginField = new JTextField();
        passwordField = new JPasswordField();

        contentPanel.add(createLabeledPanel("Login:", loginField));
        contentPanel.add(createLabeledPanel("Senha:", passwordField));

        JButton loginButton = new JButton("Entrar");
        loginButton.addActionListener(event -> handleLogin());

        JButton registerButton = new JButton("Cadastrar usuário");
        registerButton.addActionListener(event -> new TelaCadastro());

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 8, 8));
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        contentPanel.add(new JLabel(""));
        contentPanel.add(buttonPanel);

        add(contentPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    private JPanel createLabeledPanel(String labelText, JComponent field) {
        JPanel panel = new JPanel(new BorderLayout(4, 4));
        panel.add(new JLabel(labelText), BorderLayout.WEST);
        panel.add(field, BorderLayout.CENTER);
        return panel;
    }

    private void handleLogin() {
        String login = loginField.getText();
        String password = new String(passwordField.getPassword());
        try {
            Usuario usuario = authController.login(login, password).orElse(null);
            if (usuario == null) {
                JOptionPane.showMessageDialog(this, "Login ou senha inválidos.", "Erro de autenticação", JOptionPane.ERROR_MESSAGE);
                return;
            }
            new TelaPrincipal(usuario);
            dispose();
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Dados inválidos", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao efetuar login: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
