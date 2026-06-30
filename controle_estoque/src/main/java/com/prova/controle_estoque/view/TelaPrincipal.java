package com.prova.controle_estoque.view;

import com.prova.controle_estoque.controller.ControladorProduto;
import com.prova.controle_estoque.model.Categoria;
import com.prova.controle_estoque.model.Produto;
import com.prova.controle_estoque.model.Usuario;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TelaPrincipal extends JFrame {
    private final ControladorProduto productController;
    private final DefaultTableModel tableModel;
    private final JTable productTable;
    private final JTextField nameField;
    private final JTextField quantityField;
    private final JTextField priceField;
    private final JTextField searchField;
    private final JComboBox<Categoria> categoryComboBox;

    public TelaPrincipal(Usuario currentUser) {
        this.productController = new ControladorProduto(currentUser);
        setTitle("Controle de Estoque - Usuário: " + currentUser.getLogin());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(820, 520);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

        JLabel welcomeLabel = new JLabel("Bem-vindo, " + currentUser.getLogin() + "!");
        welcomeLabel.setFont(welcomeLabel.getFont().deriveFont(Font.BOLD, 14f));
        topPanel.add(welcomeLabel, BorderLayout.WEST);

        JButton logoutButton = new JButton("Sair");
        logoutButton.addActionListener(event -> handleLogout());
        topPanel.add(logoutButton, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new String[]{"ID", "Nome", "Quantidade", "Preço", "Categoria", "Cadastrado por"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        productTable = new JTable(tableModel);
        productTable.setFillsViewportHeight(true);
        productTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        productTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && productTable.getSelectedRow() >= 0) {
                    loadProductDataToFields();
                }
            }
        });
        JScrollPane tableScroll = new JScrollPane(productTable);

        add(tableScroll, BorderLayout.CENTER);

        JPanel formPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        JPanel fieldsPanel = new JPanel(new GridLayout(1, 7, 10, 10));
        nameField = new JTextField();
        quantityField = new JTextField();
        priceField = new JTextField();
        searchField = new JTextField();
        categoryComboBox = new JComboBox<>();
        loadCategoriesIntoComboBox();

        fieldsPanel.add(createLabeledPanel("Nome", nameField));
        fieldsPanel.add(createLabeledPanel("Quantidade", quantityField));
        fieldsPanel.add(createLabeledPanel("Preço", priceField));
        fieldsPanel.add(createLabeledPanel("Categoria", categoryComboBox));
        fieldsPanel.add(createLabeledPanel("Buscar por nome", searchField));
        formPanel.add(fieldsPanel);

        JPanel buttonsPanel = new JPanel(new GridLayout(1, 7, 10, 0));
        JButton addButton = new JButton("Adicionar");
        addButton.addActionListener(event -> handleAddProduct());
        JButton updateButton = new JButton("Atualizar");
        updateButton.addActionListener(event -> handleUpdateProduct());
        JButton deleteButton = new JButton("Excluir");
        deleteButton.addActionListener(event -> handleDeleteProduct());
        JButton searchButton = new JButton("Buscar");
        searchButton.addActionListener(event -> handleSearch());
        JButton refreshButton = new JButton("Atualizar lista");
        refreshButton.addActionListener(event -> loadProducts(productController.listAll()));
        JButton movementsButton = new JButton("Movimentações");
        movementsButton.addActionListener(event -> new MovementsView());
        JButton categoriesButton = new JButton("Categorias");
        categoriesButton.addActionListener(event -> new TelaCategorias(this, this::loadCategoriesIntoComboBox));

        buttonsPanel.add(addButton);
        buttonsPanel.add(updateButton);
        buttonsPanel.add(deleteButton);
        buttonsPanel.add(searchButton);
        buttonsPanel.add(refreshButton);
        buttonsPanel.add(movementsButton);
        buttonsPanel.add(categoriesButton);
        formPanel.add(buttonsPanel);

        add(formPanel, BorderLayout.SOUTH);

        loadProducts(productController.listAll());
        setVisible(true);
    }

    private static final Categoria SEM_CATEGORIA = new Categoria(-1, "(Sem categoria)");

    private void loadCategoriesIntoComboBox() {
        categoryComboBox.removeAllItems();
        categoryComboBox.addItem(SEM_CATEGORIA);
        for (Categoria categoria : productController.listCategories()) {
            categoryComboBox.addItem(categoria);
        }
    }

    private Integer getSelectedCategoryId() {
        Categoria selected = (Categoria) categoryComboBox.getSelectedItem();
        if (selected == null || selected.getId() == SEM_CATEGORIA.getId()) {
            return null;
        }
        return selected.getId();
    }

    private void selectCategoryInComboBox(Integer categoryId) {
        if (categoryId == null) {
            categoryComboBox.setSelectedItem(SEM_CATEGORIA);
            return;
        }
        for (int i = 0; i < categoryComboBox.getItemCount(); i++) {
            Categoria item = categoryComboBox.getItemAt(i);
            if (item.getId() == categoryId) {
                categoryComboBox.setSelectedIndex(i);
                return;
            }
        }
        categoryComboBox.setSelectedItem(SEM_CATEGORIA);
    }

    private JPanel createLabeledPanel(String labelText, JComponent field) {
        JPanel panel = new JPanel(new BorderLayout(4, 4));
        panel.add(new JLabel(labelText), BorderLayout.NORTH);
        panel.add(field, BorderLayout.CENTER);
        return panel;
    }

    private void loadProducts(List<Produto> products) {
        tableModel.setRowCount(0);
        for (Produto product : products) {
            tableModel.addRow(new Object[]{
                    product.getId(),
                    product.getName(),
                    product.getQuantity(),
                    String.format("%.2f", product.getPrice()),
                    product.getCategoryName() != null ? product.getCategoryName() : "(Sem categoria)",
                    product.getUserLogin()
            });
        }
    }

    private void handleAddProduct() {
        String error = productController.addProduct(nameField.getText(), quantityField.getText(), priceField.getText(), getSelectedCategoryId()).orElse(null);
        if (error != null) {
            JOptionPane.showMessageDialog(this, error, "Erro ao adicionar", JOptionPane.WARNING_MESSAGE);
            return;
        }
        loadProducts(productController.listAll());
        // selecionar o último item adicionado para feedback visual
        int rowCount = tableModel.getRowCount();
        if (rowCount > 0) {
            int lastRow = rowCount - 1;
            productTable.setRowSelectionInterval(lastRow, lastRow);
            productTable.scrollRectToVisible(productTable.getCellRect(lastRow, 0, true));
        }
        clearInputFields();
        // Atualizar a view de movimentações se estiver aberta
        MovementsView.refreshIfOpen();
    }

    private void handleUpdateProduct() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um produto na tabela para atualizar.", "Nenhum produto selecionado", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int productId = (int) tableModel.getValueAt(selectedRow, 0);
        String error = productController.updateProduct(productId, nameField.getText(), quantityField.getText(), priceField.getText(), getSelectedCategoryId()).orElse(null);
        if (error != null) {
            JOptionPane.showMessageDialog(this, error, "Erro ao atualizar", JOptionPane.WARNING_MESSAGE);
            return;
        }
        loadProducts(productController.listAll());
        clearInputFields();
        // Atualizar a view de movimentações se estiver aberta
        MovementsView.refreshIfOpen();
    }

    private void handleDeleteProduct() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um produto na tabela para excluir.", "Nenhum produto selecionado", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int productId = (int) tableModel.getValueAt(selectedRow, 0);
        int result = JOptionPane.showConfirmDialog(this, "Deseja excluir o produto selecionado?", "Confirmar exclusão", JOptionPane.YES_NO_OPTION);
        if (result != JOptionPane.YES_OPTION) {
            return;
        }
        System.out.println("[DEBUG] Deletando produto ID: " + productId);
        if (!productController.deleteProduct(productId)) {
            JOptionPane.showMessageDialog(this, "Falha ao excluir produto.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        System.out.println("[DEBUG] Produto deletado com sucesso");
        loadProducts(productController.listAll());
        clearInputFields();
        // Atualizar a view de movimentações se estiver aberta
        System.out.println("[DEBUG] Chamando refreshIfOpen() após deletar");
        MovementsView.refreshIfOpen();
    }

    private void handleSearch() {
        loadProducts(productController.searchByName(searchField.getText()));
    }

    private void handleLogout() {
        dispose();
        new TelaLogin();
    }

    private void clearInputFields() {
        nameField.setText("");
        quantityField.setText("");
        priceField.setText("");
        categoryComboBox.setSelectedItem(SEM_CATEGORIA);
    }

    private void loadProductDataToFields() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow < 0) {
            return;
        }
        int productId = (int) tableModel.getValueAt(selectedRow, 0);
        Produto product = productController.getProductById(productId);
        if (product != null) {
            nameField.setText(product.getName());
            quantityField.setText(String.valueOf(product.getQuantity()));
            priceField.setText(String.valueOf(product.getPrice()));
            selectCategoryInComboBox(product.getCategoryId());
        }
    }
}
