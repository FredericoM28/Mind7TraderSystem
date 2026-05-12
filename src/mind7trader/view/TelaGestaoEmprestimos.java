package view;

import control.GestaoEmprestimoController;
import control.GestaoClienteController;
import model.Emprestimo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TelaGestaoEmprestimos extends JPanel {

    private GestaoEmprestimoController controller;
    private GestaoClienteController clienteController;
    private JTable table;
    private DefaultTableModel tableModel;
    
    private JTextField txtNumeroConta;
    private JTextField txtValor;
    private JTextField txtPagamento;

    public TelaGestaoEmprestimos() {
        controller = new GestaoEmprestimoController();
        clienteController = new GestaoClienteController();
        initComponents();
        carregarEmprestimos();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(240, 248, 245));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Título
        JLabel titulo = new JLabel("Gestão de Empréstimos");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(new Color(20, 40, 80));
        add(titulo, BorderLayout.NORTH);

        // Painel superior com formulários
        JPanel topPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        topPanel.setBackground(new Color(240, 248, 245));

        // Solicitar Empréstimo
        JPanel solicitarPanel = new JPanel(new GridBagLayout());
        solicitarPanel.setBackground(new Color(240, 248, 245));
        solicitarPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(0, 153, 102)), "Solicitar Empréstimo"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        solicitarPanel.add(new JLabel("Nº Conta:"), gbc);
        gbc.gridx = 1;
        txtNumeroConta = new JTextField(12);
        solicitarPanel.add(txtNumeroConta, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        solicitarPanel.add(new JLabel("Valor (MT):"), gbc);
        gbc.gridx = 1;
        txtValor = new JTextField(12);
        solicitarPanel.add(txtValor, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        JButton btnSolicitar = createButton(" Solicitar", new Color(0, 153, 102));
        btnSolicitar.addActionListener(e -> solicitarEmprestimo());
        solicitarPanel.add(btnSolicitar, gbc);

        // Pagar Empréstimo
        JPanel pagarPanel = new JPanel(new GridBagLayout());
        pagarPanel.setBackground(new Color(240, 248, 245));
        pagarPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(200, 100, 50)), "Pagar Empréstimo"));
        
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        pagarPanel.add(new JLabel("ID Empréstimo:"), gbc);
        gbc.gridx = 1;
        JTextField txtIdEmprestimo = new JTextField(12);
        pagarPanel.add(txtIdEmprestimo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        pagarPanel.add(new JLabel("Valor Pagamento:"), gbc);
        gbc.gridx = 1;
        txtPagamento = new JTextField(12);
        pagarPanel.add(txtPagamento, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        JButton btnPagar = createButton(" Pagar", new Color(200, 100, 50));
        btnPagar.addActionListener(e -> pagarEmprestimo(txtIdEmprestimo.getText().trim()));
        pagarPanel.add(btnPagar, gbc);

        topPanel.add(solicitarPanel);
        topPanel.add(pagarPanel);
        add(topPanel, BorderLayout.NORTH);

        // Informação da regra
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        infoPanel.setBackground(new Color(240, 248, 245));
        JLabel lblInfo = new JLabel(" Regra: Empréstimo não pode ser maior que a soma das poupanças dos últimos 2 meses. Juros: 20% ao mês.");
        lblInfo.setFont(new Font("Arial", Font.ITALIC, 11));
        lblInfo.setForeground(new Color(100, 100, 100));
        infoPanel.add(lblInfo);
        add(infoPanel, BorderLayout.CENTER);

        // Tabela de empréstimos
        String[] colunas = {"ID", "Cliente", "Valor Solicitado", "Valor com Juros", "Dívida Atual", "Vencimento", "Status"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        table.setRowHeight(28);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        table.getTableHeader().setBackground(new Color(20, 40, 80));
        table.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Empréstimos"));
        add(scrollPane, BorderLayout.SOUTH);

        JButton btnAtualizar = createButton(" Atualizar", new Color(70, 130, 180));
        btnAtualizar.addActionListener(e -> carregarEmprestimos());
        
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBackground(new Color(240, 248, 245));
        bottomPanel.add(btnAtualizar);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private JButton createButton(String text, Color bgColor) {
        JButton btn = new JButton(text);
        btn.setBackground(bgColor);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        return btn;
    }

    private void solicitarEmprestimo() {
        String numeroConta = txtNumeroConta.getText().trim();
        String valorStr = txtValor.getText().trim();

        if (numeroConta.isEmpty() || valorStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        double valor;
        try {
            valor = Double.parseDouble(valorStr);
            if (valor <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Valor inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Verificar se cliente existe
        var cliente = clienteController.buscarClientePorNumeroConta(numeroConta);
        if (cliente == null) {
            JOptionPane.showMessageDialog(this, "Cliente não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Emprestimo emprestimo = controller.solicitarEmprestimo(numeroConta, null, valor);
        
        if (emprestimo != null) {
            JOptionPane.showMessageDialog(this, 
                "Empréstimo solicitado com sucesso!\n" +
                "Valor solicitado: " + String.format("%,.2f", valor) + " MT\n" +
                "Valor a pagar em 1 mês: " + String.format("%,.2f", emprestimo.getValorComJuros()) + " MT\n" +
                "Vencimento: " + emprestimo.getDataVencimento(), 
                "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            
            txtNumeroConta.setText("");
            txtValor.setText("");
            carregarEmprestimos();
        } else {
            JOptionPane.showMessageDialog(this, 
                "Empréstimo negado!\nO valor solicitado excede a soma das poupanças dos últimos 2 meses.", 
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void pagarEmprestimo(String idEmprestimo) {
        if (idEmprestimo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite o ID do empréstimo!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String valorStr = txtPagamento.getText().trim();
        if (valorStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite o valor do pagamento!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        double valor;
        try {
            valor = Double.parseDouble(valorStr);
            if (valor <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Valor inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean sucesso = controller.pagarEmprestimo(idEmprestimo, valor);
        
        if (sucesso) {
            JOptionPane.showMessageDialog(this, "Pagamento realizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            txtPagamento.setText("");
            carregarEmprestimos();
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao realizar pagamento!\nVerifique o ID e o valor.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void carregarEmprestimos() {
        tableModel.setRowCount(0);
        // Mostrar todos os empréstimos (simplificado)
       /*  JOptionPane.showMessageDialog(this, "Funcionalidade de listagem em desenvolvimento.\nUse o menu lateral para outras funções.", "Info", JOptionPane.INFORMATION_MESSAGE);*/
    }
}