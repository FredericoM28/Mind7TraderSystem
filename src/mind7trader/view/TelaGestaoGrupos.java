package view;

import control.GestaoGrupoController;
import model.Grupo;
import model.Grupo.TipoCiclo;
import model.Grupo.TipoPeriodo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TelaGestaoGrupos extends JPanel {

    private GestaoGrupoController controller;
    private JTable table;
    private DefaultTableModel tableModel;
    private JComboBox<String> cmbPeriodo;
    private JComboBox<String> cmbCiclo;
    private JTextField txtNomeGrupo;

    public TelaGestaoGrupos() {
        controller = new GestaoGrupoController();
        initComponents();
        carregarGrupos();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(240, 248, 245));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Título
        JLabel titulo = new JLabel("Gestão de Grupos");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(new Color(20, 40, 80));
        add(titulo, BorderLayout.NORTH);

        // Painel de formulário
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(240, 248, 245));
        formPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(0, 153, 102)), "Criar Novo Grupo"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Nome do Grupo:"), gbc);
        gbc.gridx = 1;
        txtNomeGrupo = new JTextField(20);
        formPanel.add(txtNomeGrupo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Período:"), gbc);
        gbc.gridx = 1;
        cmbPeriodo = new JComboBox<>(new String[]{"SEMANAL", "MENSAL"});
        formPanel.add(cmbPeriodo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Ciclo:"), gbc);
        gbc.gridx = 1;
        cmbCiclo = new JComboBox<>(new String[]{"SEIS_MESES (6 meses)", "NOVE_MESES (9 meses)", "DOZE_MESES (12 meses)"});
        formPanel.add(cmbCiclo, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        JButton btnCriar = createButton("➕ Criar Grupo", new Color(0, 153, 102));
        btnCriar.addActionListener(e -> criarGrupo());
        formPanel.add(btnCriar, gbc);

        add(formPanel, BorderLayout.NORTH);

        // Tabela de grupos
        String[] colunas = {"ID", "Nome", "Período", "Ciclo", "Membros", "Saldo Total", "Status"};
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
        scrollPane.setBorder(BorderFactory.createTitledBorder("Grupos Cadastrados"));
        add(scrollPane, BorderLayout.CENTER);

        // Painel de ações
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        actionPanel.setBackground(new Color(240, 248, 245));
        
        JButton btnAtualizar = createButton("🔄 Atualizar", new Color(70, 130, 180));
        JButton btnFinalizar = createButton("🏁 Finalizar Grupo", new Color(200, 100, 50));
        
        btnAtualizar.addActionListener(e -> carregarGrupos());
        btnFinalizar.addActionListener(e -> finalizarGrupo());
        
        actionPanel.add(btnAtualizar);
        actionPanel.add(btnFinalizar);
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

    private void criarGrupo() {
        String nome = txtNomeGrupo.getText().trim();
        if (nome.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite o nome do grupo!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        TipoPeriodo periodo = cmbPeriodo.getSelectedItem().equals("SEMANAL") ? TipoPeriodo.SEMANAL : TipoPeriodo.MENSAL;
        
        TipoCiclo ciclo;
        String selected = cmbCiclo.getSelectedItem().toString();
        if (selected.contains("SEIS")) ciclo = TipoCiclo.SEIS_MESES;
        else if (selected.contains("NOVE")) ciclo = TipoCiclo.NOVE_MESES;
        else ciclo = TipoCiclo.DOZE_MESES;

        Grupo grupo = controller.criarGrupo(nome, ciclo, periodo);
        if (grupo != null) {
            JOptionPane.showMessageDialog(this, "Grupo criado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            txtNomeGrupo.setText("");
            carregarGrupos();
        }
    }

    private void carregarGrupos() {
        tableModel.setRowCount(0);
        List<Grupo> grupos = controller.listarTodosGruposAtivos();
        for (Grupo g : grupos) {
            tableModel.addRow(new Object[]{
                g.getId().substring(0, 8) + "...",
                g.getNome(),
                g.getPeriodo(),
                g.getCiclo().toString().replace("_", " "),
                g.getQuantidadeMembros(),
                String.format("%,.2f", g.getSaldoTotalGrupo()),
                g.getStatus()
            });
        }
    }

    private void finalizarGrupo() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um grupo para finalizar!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Deseja finalizar este grupo? (Não será possível reverter)", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            // Nota: Precisamos do ID completo. Como está truncado, ideal seria ter o ID na tabela escondido
            JOptionPane.showMessageDialog(this, "Funcionalidade em desenvolvimento completo.\nSelecione um grupo válido.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}