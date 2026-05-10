package view;

import control.GestaoPoupancaController;
import control.GestaoClienteController;
import control.GestaoGrupoController;
import model.Poupanca;
import model.Poupanca.TipoCiclo;
import model.Poupanca.TipoPeriodo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TelaGestaoPoupancas extends JPanel {

    private GestaoPoupancaController controller;
    private GestaoClienteController clienteController;
    private GestaoGrupoController grupoController;
    private JTable table;
    private DefaultTableModel tableModel;
    
    private JTextField txtNumeroConta;
    private JTextField txtValor;
    private JComboBox<String> cmbGrupo;
    private JComboBox<String> cmbPeriodo;
    private JComboBox<String> cmbCiclo;

    public TelaGestaoPoupancas() {
        controller = new GestaoPoupancaController();
        clienteController = new GestaoClienteController();
        grupoController = new GestaoGrupoController();
        initComponents();
        carregarPoupancas();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(240, 248, 245));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Título
        JLabel titulo = new JLabel("Gestão de Poupanças");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(new Color(20, 40, 80));
        add(titulo, BorderLayout.NORTH);

        // Painel de formulário
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(240, 248, 245));
        formPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(0, 153, 102)), "Criar Nova Poupança"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Nº Conta do Cliente:"), gbc);
        gbc.gridx = 1;
        txtNumeroConta = new JTextField(15);
        formPanel.add(txtNumeroConta, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Valor a Investir (MT):"), gbc);
        gbc.gridx = 1;
        txtValor = new JTextField(15);
        formPanel.add(txtValor, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Grupo:"), gbc);
        gbc.gridx = 1;
        cmbGrupo = new JComboBox<>(new String[]{"Sem grupo", "Grupo A", "Grupo B"});
        formPanel.add(cmbGrupo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(new JLabel("Período:"), gbc);
        gbc.gridx = 1;
        cmbPeriodo = new JComboBox<>(new String[]{"SEMANAL", "MENSAL"});
        formPanel.add(cmbPeriodo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(new JLabel("Ciclo:"), gbc);
        gbc.gridx = 1;
        cmbCiclo = new JComboBox<>(new String[]{"6 meses", "9 meses", "12 meses"});
        formPanel.add(cmbCiclo, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        JButton btnCriar = createButton(" Criar Poupança", new Color(0, 153, 102));
        btnCriar.addActionListener(e -> criarPoupanca());
        formPanel.add(btnCriar, gbc);

        // Informação da taxa
        JLabel lblTaxa = new JLabel(" Taxa de poupança: 50% (Ex: investe 500 MT → recebe 750 MT no final)");
        lblTaxa.setFont(new Font("Arial", Font.ITALIC, 11));
        lblTaxa.setForeground(new Color(100, 100, 100));
        gbc.gridx = 1;
        gbc.gridy = 6;
        formPanel.add(lblTaxa, gbc);

        add(formPanel, BorderLayout.NORTH);

        // Tabela de poupanças
        String[] colunas = {"ID", "Cliente", "Valor Investido", "Valor Final", "Data Fim", "Ciclo", "Status"};
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
        scrollPane.setBorder(BorderFactory.createTitledBorder("Poupanças Ativas"));
        add(scrollPane, BorderLayout.CENTER);

        // Botões de ação
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        actionPanel.setBackground(new Color(240, 248, 245));
        
        JButton btnAtualizar = createButton(" Atualizar", new Color(70, 130, 180));
        JButton btnConcluir = createButton(" Concluir Poupança", new Color(0, 120, 80));
        JButton btnCancelar = createButton(" Cancelar", new Color(200, 60, 60));
        
        btnAtualizar.addActionListener(e -> carregarPoupancas());
        btnConcluir.addActionListener(e -> concluirPoupanca());
        btnCancelar.addActionListener(e -> cancelarPoupanca());
        
        actionPanel.add(btnAtualizar);
        actionPanel.add(btnConcluir);
        actionPanel.add(btnCancelar);
        add(actionPanel, BorderLayout.SOUTH);
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

    private void criarPoupanca() {
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

        // Verificar saldo
        if (cliente.getSaldo() < valor) {
            JOptionPane.showMessageDialog(this, "Saldo insuficiente!\nSaldo atual: " + String.format("%,.2f", cliente.getSaldo()) + " MT", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        TipoPeriodo periodo = cmbPeriodo.getSelectedItem().equals("SEMANAL") ? TipoPeriodo.SEMANAL : TipoPeriodo.MENSAL;
        
        TipoCiclo ciclo;
        String selected = cmbCiclo.getSelectedItem().toString();
        if (selected.equals("6 meses")) ciclo = TipoCiclo.SEIS_MESES;
        else if (selected.equals("9 meses")) ciclo = TipoCiclo.NOVE_MESES;
        else ciclo = TipoCiclo.DOZE_MESES;

        Poupanca poupanca = controller.criarPoupanca(numeroConta, null, valor, ciclo, periodo);
        
        if (poupanca != null) {
            JOptionPane.showMessageDialog(this, 
                "Poupança criada com sucesso!\n" +
                "Valor investido: " + String.format("%,.2f", valor) + " MT\n" +
                "Valor a receber no final: " + String.format("%,.2f", poupanca.getValorTotalComJuros()) + " MT\n" +
                "Data de conclusão: " + poupanca.getDataFim(), 
                "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            
            txtNumeroConta.setText("");
            txtValor.setText("");
            carregarPoupancas();
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao criar poupança!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void carregarPoupancas() {
        tableModel.setRowCount(0);
        List<Poupanca> poupancas = controller.listarPoupancasAtivas();
        for (Poupanca p : poupancas) {
            tableModel.addRow(new Object[]{
                p.getId().substring(0, 8) + "...",
                p.getNumeroContaCliente(),
                String.format("%,.2f", p.getValorInvestido()),
                String.format("%,.2f", p.getValorTotalComJuros()),
                p.getDataFim(),
                p.getCiclo().toString().replace("_", " "),
                p.getStatus()
            });
        }
    }

    private void concluirPoupanca() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma poupança para concluir!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        JOptionPane.showMessageDialog(this, "Selecione uma poupança válida para concluir.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
    }

    private void cancelarPoupanca() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma poupança para cancelar!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        JOptionPane.showMessageDialog(this, "Selecione uma poupança válida para cancelar.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
    }
}