package com.prova.controle_estoque.view;

import com.prova.controle_estoque.controller.ControladorCategoria;
import com.prova.controle_estoque.model.Categoria;

import javax.swing.*;
import java.awt.*;

public class TelaCategorias extends JDialog {
    private final ControladorCategoria categoriaController = new ControladorCategoria();
    private final DefaultListModel<Categoria> listModel;
    private final JList<Categoria> categoryList;
    private final JTextField nameField;
    private final Runnable onChangeCallback;

    public TelaCategorias(Frame owner, Runnable onChangeCallback) {
        super(owner, "Gerenciar Categorias", true);
        this.onChangeCallback = onChangeCallback;
        setSize(380, 420);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout(10, 10));

        listModel = new DefaultListModel<>();
        categoryList = new JList<>(listModel);
        JScrollPane listScroll = new JScrollPane(categoryList);
        listScroll.setBorder(BorderFactory.createTitledBorder("Categorias cadastradas"));
        add(listScroll, BorderLayout.CENTER);

        JPanel formPanel = new JPanel(new BorderLayout(10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        nameField = new JTextField();
        formPanel.add(new JLabel("Nova categoria:"), BorderLayout.NORTH);
        formPanel.add(nameField, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        JButton addButton = new JButton("Adicionar");
        addButton.addActionListener(event -> handleAdd());
        JButton deleteButton = new JButton("Excluir selecionada");
        deleteButton.addActionListener(event -> handleDelete());
        buttonsPanel.add(addButton);
        buttonsPanel.add(deleteButton);
        formPanel.add(buttonsPanel, BorderLayout.SOUTH);

        add(formPanel, BorderLayout.SOUTH);

        loadCategories();
        setVisible(true);
    }

    private void loadCategories() {
        listModel.clear();
        for (Categoria categoria : categoriaController.listAll()) {
            listModel.addElement(categoria);
        }
    }

    private void handleAdd() {
        String error = categoriaController.addCategory(nameField.getText()).orElse(null);
        if (error != null) {
            JOptionPane.showMessageDialog(this, error, "Erro ao cadastrar", JOptionPane.WARNING_MESSAGE);
            return;
        }
        nameField.setText("");
        loadCategories();
        notifyChange();
    }

    private void handleDelete() {
        Categoria selected = categoryList.getSelectedValue();
        if (selected == null) {
            JOptionPane.showMessageDialog(this, "Selecione uma categoria na lista para excluir.", "Nenhuma categoria selecionada", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int result = JOptionPane.showConfirmDialog(this,
                "Excluir a categoria \"" + selected.getName() + "\"? Produtos que usam essa categoria ficarão sem categoria.",
                "Confirmar exclusão", JOptionPane.YES_NO_OPTION);
        if (result != JOptionPane.YES_OPTION) {
            return;
        }
        if (!categoriaController.deleteCategory(selected.getId())) {
            JOptionPane.showMessageDialog(this, "Falha ao excluir categoria.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        loadCategories();
        notifyChange();
    }

    private void notifyChange() {
        if (onChangeCallback != null) {
            onChangeCallback.run();
        }
    }
}
