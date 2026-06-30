package com.prova.controle_estoque.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.prova.controle_estoque.dao.MovementLogDAO;
import com.prova.controle_estoque.model.MovementLog;

public class MovementsView extends JFrame {
    private DefaultTableModel tableModel;
    private JTable logsTable;
    private static MovementsView currentInstance;

    public MovementsView() {
        if (currentInstance != null) {
            System.out.println("[DEBUG] MovementsView já está aberta, trazendo para foco");
            currentInstance.setVisible(true);
            currentInstance.toFront();
            currentInstance.requestFocus();
            return;
        }
        
        currentInstance = this;
        System.out.println("[DEBUG] Criando nova instância de MovementsView");
        setupUI();
    }

    private void setupUI() {
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                System.out.println("[DEBUG] MovementsView fechada (windowClosed)");
                currentInstance = null;
            }

            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                System.out.println("[DEBUG] MovementsView fechando (windowClosing)");
                currentInstance = null;
            }
        });
        setTitle("Histórico de Movimentações");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JLabel titleLabel = new JLabel("Histórico de Movimentações e Transações");
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 14f));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        add(titleLabel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(
                new String[]{"ID", "Produto", "Ação", "Quantidade", "Usuário", "Data/Hora"},
                0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        logsTable = new JTable(tableModel);
        logsTable.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(logsTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton refreshButton = new JButton("Atualizar");
        refreshButton.addActionListener(event -> loadMovements());
        JButton closeButton = new JButton("Fechar");
        closeButton.addActionListener(event -> dispose());
        buttonPanel.add(refreshButton);
        buttonPanel.add(closeButton);
        add(buttonPanel, BorderLayout.SOUTH);

        loadMovements();
        setVisible(true);
    }

    public void loadMovements() {
        tableModel.setRowCount(0);
        try {
            List<MovementLog> logs = new MovementLogDAO().getAllLogs();
            System.out.println("[DEBUG] Carregados " + logs.size() + " logs de movimentação");
            for (MovementLog log : logs) {
                System.out.println("[DEBUG] Log: ID=" + log.getId() + ", Produto=" + log.getProductName() + ", Ação=" + log.getAction());
                tableModel.addRow(new Object[]{
                        log.getId(),
                        log.getProductName(),
                        log.getAction(),
                        log.getQuantityChanged(),
                        log.getUserLogin(),
                        log.getTimestamp()
                });
            }
        } catch (Exception e) {
            System.err.println("[DEBUG] ERRO ao carregar movimentações: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao carregar movimentações: " + e.getMessage(), 
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void refreshIfOpen() {
        System.out.println("[DEBUG] refreshIfOpen() chamado. currentInstance = " + currentInstance);
        if (currentInstance != null) {
            System.out.println("[DEBUG] Recarregando movimentações");
            currentInstance.loadMovements();
            System.out.println("[DEBUG] Movimentações recarregadas");
        } else {
            System.out.println("[DEBUG] MovementsView não está aberta");
        }
    }
}
