package view;

import control.GestaoClienteController;
import model.Cliente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class TelaListaClientes extends JPanel {

    private GestaoClienteController controller;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtBusca;
    private JButton btnBuscar;
    private JButton btnCadastrar;
    private JButton btnAtualizar;
    private JButton btnDesativar;

    public TelaListaClientes() {
        controller = new GestaoClienteController();
        initComponents();
        carregarClientes();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(240, 248, 245));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Título
        JLabel titulo = new JLabel("Gestão de Clientes");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(new Color(20, 40, 80));
        add(titulo, BorderLayout.NORTH);

        // Painel de busca
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBackground(new Color(240, 248, 245));
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        txtBusca = new JTextField(20);
        txtBusca.setFont(new Font("Arial", Font.PLAIN, 14));
        btnBuscar = createButton(" Buscar", new Color(0, 153, 102));
        btnCadastrar = createButton(" Novo Cliente", new Color(0, 120, 80));
        btnAtualizar = createButton(" Atualizar", new Color(70, 130, 180));
        btnDesativar = createButton(" Desativar", new Color(200, 60, 60));

        searchPanel.add(new JLabel("Buscar: "));
        searchPanel.add(txtBusca);
        searchPanel.add(btnBuscar);
        searchPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        searchPanel.add(btnCadastrar);
        searchPanel.add(btnAtualizar);
        searchPanel.add(btnDesativar);

        add(searchPanel, BorderLayout.NORTH);

        // Tabela
        String[] colunas = {"Nº Conta", "Nome", "BI", "Telefone", "Email", "Saldo (MT)", "Status"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setFont(new Font("Arial", Font.PLAIN, 13));
        table.setRowHeight(30);
        table.setSelectionBackground(new Color(0, 153, 102, 50));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        table.getTableHeader().setBackground(new Color(20, 40, 80));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setPreferredSize(new Dimension(100, 35));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        add(scrollPane, BorderLayout.CENTER);

        // Ações
        btnBuscar.addActionListener(e -> buscarClientes());
        btnCadastrar.addActionListener(e -> new TelaCadastro());
        btnAtualizar.addActionListener(e -> carregarClientes());
        btnDesativar.addActionListener(e -> desativarCliente());
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

    private void carregarClientes() {
        tableModel.setRowCount(0);
        List<Cliente> clientes = controller.listarTodosClientes();
        for (Cliente c : clientes) {
            tableModel.addRow(new Object[]{
                c.getNumeroConta(),
                c.getNome(),
                c.getBi(),
                c.getTelefone(),
                c.getEmail(),
                String.format("%,.2f", c.getSaldo()),
                c.isAtivo() ? "Ativo" : "Inativo"
            });
        }
    }

    private void buscarClientes() {
        String busca = txtBusca.getText().trim();
        if (busca.isEmpty()) {
            carregarClientes();
            return;
        }

        tableModel.setRowCount(0);
        Cliente cliente = controller.buscarClientePorNumeroConta(busca);
        if (cliente == null) {
            cliente = controller.buscarClientePorBI(busca);
        }

        if (cliente != null && cliente.isAtivo()) {
            tableModel.addRow(new Object[]{
                cliente.getNumeroConta(),
                cliente.getNome(),
                cliente.getBi(),
                cliente.getTelefone(),
                cliente.getEmail(),
                String.format("%,.2f", cliente.getSaldo()),
                cliente.isAtivo() ? "Ativo" : "Inativo"
            });
        } else {
            JOptionPane.showMessageDialog(this, "Cliente não encontrado!", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void desativarCliente() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente para desativar!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String numeroConta = tableModel.getValueAt(selectedRow, 0).toString();
        int confirm = JOptionPane.showConfirmDialog(this, "Deseja desativar este cliente?", "Confirmar", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            boolean sucesso = controller.desativarCliente(numeroConta);
            if (sucesso) {
                JOptionPane.showMessageDialog(this, "Cliente desativado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                carregarClientes();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao desativar cliente!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}