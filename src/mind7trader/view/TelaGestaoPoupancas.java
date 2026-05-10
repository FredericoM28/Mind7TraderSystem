package view;

import control.GestaoPoupancaController;
import control.GestaoClienteController;
import control.GestaoGrupoController;
import model.Poupanca;
import model.Poupanca.TipoCiclo;
import model.Poupanca.TipoPeriodo;
import model.Grupo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

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
    
    private List<Grupo> listaGrupos;

    public TelaGestaoPoupancas() {
        controller = new GestaoPoupancaController();
        clienteController = new GestaoClienteController();
        grupoController = new GestaoGrupoController();
        listaGrupos = new ArrayList<>();
        initComponents();
        carregarGruposReais();
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
        txtNumeroConta.setFont(new Font("Arial", Font.PLAIN, 13));
        formPanel.add(txtNumeroConta, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Valor a Investir (MT):"), gbc);
        gbc.gridx = 1;
        txtValor = new JTextField(15);
        txtValor.setFont(new Font("Arial", Font.PLAIN, 13));
        formPanel.add(txtValor, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Grupo:"), gbc);
        gbc.gridx = 1;
        cmbGrupo = new JComboBox<>();
        cmbGrupo.setFont(new Font("Arial", Font.PLAIN, 13));
        formPanel.add(cmbGrupo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(new JLabel("Período:"), gbc);
        gbc.gridx = 1;
        cmbPeriodo = new JComboBox<>(new String[]{"SEMANAL", "MENSAL"});
        cmbPeriodo.setFont(new Font("Arial", Font.PLAIN, 13));
        formPanel.add(cmbPeriodo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(new JLabel("Ciclo:"), gbc);
        gbc.gridx = 1;
        cmbCiclo = new JComboBox<>(new String[]{"6 meses", "9 meses", "12 meses"});
        cmbCiclo.setFont(new Font("Arial", Font.PLAIN, 13));
        formPanel.add(cmbCiclo, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        JButton btnCriar = createButton("💰 Criar Poupança", new Color(0, 153, 102));
        btnCriar.addActionListener(e -> criarPoupanca());
        formPanel.add(btnCriar, gbc);

        // Informação da taxa
        JLabel lblTaxa = new JLabel("ℹ️ Taxa de poupança: 50% (Ex: investe 500 MT → recebe 750 MT no final)");
        lblTaxa.setFont(new Font("Arial", Font.ITALIC, 11));
        lblTaxa.setForeground(new Color(100, 100, 100));
        gbc.gridx = 1;
        gbc.gridy = 6;
        formPanel.add(lblTaxa, gbc);
        
        // Informação importante - SEM VERIFICAÇÃO DE SALDO
        JLabel lblInfoSaldo = new JLabel("📌 Nota: O cliente NÃO precisa ter saldo prévio. A poupança é o investimento inicial.");
        lblInfoSaldo.setFont(new Font("Arial", Font.PLAIN, 10));
        lblInfoSaldo.setForeground(new Color(0, 100, 150));
        gbc.gridx = 1;
        gbc.gridy = 7;
        formPanel.add(lblInfoSaldo, gbc);

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
        table.setSelectionBackground(new Color(0, 153, 102, 50));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        table.getTableHeader().setBackground(new Color(20, 40, 80));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setPreferredSize(new Dimension(100, 32));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(0, 153, 102)), "Poupanças Ativas"));
        add(scrollPane, BorderLayout.CENTER);

        // Botões de ação
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        actionPanel.setBackground(new Color(240, 248, 245));
        
        JButton btnAtualizar = createButton("🔄 Atualizar", new Color(70, 130, 180));
        JButton btnConcluir = createButton("✅ Concluir Poupança", new Color(0, 120, 80));
        JButton btnCancelar = createButton("❌ Cancelar", new Color(200, 60, 60));
        
        btnAtualizar.addActionListener(e -> {
            carregarGruposReais();
            carregarPoupancas();
        });
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
    
    private void carregarGruposReais() {
        cmbGrupo.removeAllItems();
        listaGrupos.clear();
        
        // Adicionar opção "Sem grupo"
        cmbGrupo.addItem("-- Sem grupo --");
        listaGrupos.add(null);
        
        // Carregar grupos ativos
        List<Grupo> grupos = grupoController.listarTodosGruposAtivos();
        
        System.out.println("Carregando grupos... Total encontrado: " + (grupos != null ? grupos.size() : 0));
        
        if (grupos != null && !grupos.isEmpty()) {
            for (Grupo grupo : grupos) {
                String nomeGrupo = grupo.getNome() + " (" + grupo.getPeriodo() + " - " + 
                                  grupo.getCiclo().toString().replace("_", " ") + ")";
                cmbGrupo.addItem(nomeGrupo);
                listaGrupos.add(grupo);
                System.out.println("Grupo adicionado: " + nomeGrupo);
            }
        } else {
            cmbGrupo.addItem("-- Nenhum grupo disponível --");
            listaGrupos.add(null);
            System.out.println("Nenhum grupo encontrado!");
        }
    }
    
    private String getIdGrupoSelecionado() {
        int index = cmbGrupo.getSelectedIndex();
        if (index >= 0 && index < listaGrupos.size()) {
            Grupo grupo = listaGrupos.get(index);
            return grupo != null ? grupo.getId() : null;
        }
        return null;
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
            JOptionPane.showMessageDialog(this, "Valor inválido! Digite um número positivo.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Verificar se cliente existe
        var cliente = clienteController.buscarClientePorNumeroConta(numeroConta);
        if (cliente == null) {
            JOptionPane.showMessageDialog(this, "Cliente não encontrado!\nVerifique o número da conta.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Verificar se o cliente está ativo
        if (!cliente.isAtivo()) {
            JOptionPane.showMessageDialog(this, "Cliente está desativado!\nNão é possível criar poupança.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // NÃO VERIFICAR SALDO! O cliente pode investir mesmo sem saldo prévio

        TipoPeriodo periodo = cmbPeriodo.getSelectedItem().equals("SEMANAL") ? TipoPeriodo.SEMANAL : TipoPeriodo.MENSAL;
        
        TipoCiclo ciclo;
        String selected = cmbCiclo.getSelectedItem().toString();
        if (selected.equals("6 meses")) ciclo = TipoCiclo.SEIS_MESES;
        else if (selected.equals("9 meses")) ciclo = TipoCiclo.NOVE_MESES;
        else ciclo = TipoCiclo.DOZE_MESES;

        String idGrupo = getIdGrupoSelecionado();
        
        // Confirmar criação da poupança
        int confirm = JOptionPane.showConfirmDialog(this,
            "Confirmar criação da poupança?\n\n" +
            "Cliente: " + cliente.getNome() + "\n" +
            "Nº Conta: " + cliente.getNumeroConta() + "\n" +
            "Valor investido: " + String.format("%,.2f", valor) + " MT\n" +
            "Taxa: 50%\n" +
            "Valor a receber no final: " + String.format("%,.2f", valor * 1.5) + " MT\n" +
            "Lucro: " + String.format("%,.2f", valor * 0.5) + " MT\n" +
            "Período: " + periodo + "\n" +
            "Ciclo: " + selected + "\n" +
            "Grupo: " + cmbGrupo.getSelectedItem() + "\n\n" +
            "Deseja continuar?",
            "Confirmar Poupança",
            JOptionPane.YES_NO_OPTION);
            
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        Poupanca poupanca = controller.criarPoupanca(numeroConta, idGrupo, valor, ciclo, periodo);
        
        if (poupanca != null) {
            JOptionPane.showMessageDialog(this, 
                "✅ Poupança criada com sucesso!\n\n" +
                "📊 Detalhes da Poupança:\n" +
                "─────────────────────\n" +
                "💰 Valor investido: " + String.format("%,.2f", valor) + " MT\n" +
                "📈 Valor a receber: " + String.format("%,.2f", poupanca.getValorTotalComJuros()) + " MT\n" +
                "💵 Lucro: " + String.format("%,.2f", poupanca.calcularLucro()) + " MT\n" +
                "📅 Data de conclusão: " + poupanca.getDataFim() + "\n" +
                "⏰ Dias restantes: " + poupanca.getDiasRestantes() + " dias", 
                "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            
            txtNumeroConta.setText("");
            txtValor.setText("");
            carregarPoupancas();
        } else {
            JOptionPane.showMessageDialog(this, 
                "❌ Erro ao criar poupança!\n\n" +
                "Verifique se o cliente existe e tente novamente.", 
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void carregarPoupancas() {
        tableModel.setRowCount(0);
        List<Poupanca> poupancas = controller.listarPoupancasAtivas();
        
        if (poupancas == null || poupancas.isEmpty()) {
            tableModel.addRow(new Object[]{"--", "--", "--", "--", "--", "--", "Nenhuma poupança ativa"});
            return;
        }
        
        for (Poupanca p : poupancas) {
            tableModel.addRow(new Object[]{
                p.getId().substring(0, Math.min(8, p.getId().length())) + "...",
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
        
        Object idObj = tableModel.getValueAt(selectedRow, 0);
        if (idObj.toString().equals("--")) {
            JOptionPane.showMessageDialog(this, "Nenhuma poupança válida selecionada!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        JOptionPane.showMessageDialog(this, 
            "Funcionalidade em desenvolvimento.\n\n" +
            "Para concluir uma poupança, selecione-a e clique em 'Concluir'.\n" +
            "O sistema automaticamente adicionará o valor + juros ao saldo do cliente.", 
            "Informação", JOptionPane.INFORMATION_MESSAGE);
    }

    private void cancelarPoupanca() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma poupança para cancelar!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Object idObj = tableModel.getValueAt(selectedRow, 0);
        if (idObj.toString().equals("--")) {
            JOptionPane.showMessageDialog(this, "Nenhuma poupança válida selecionada!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this,
            "Deseja realmente cancelar esta poupança?\n\n" +
            "⚠️ ATENÇÃO: O valor investido será DEVOLVIDO ao cliente,\n" +
            "mas os JUROS serão PERDIDOS.\n\n" +
            "Esta ação não pode ser desfeita!",
            "Cancelar Poupança",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
            
        if (confirm == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(this, 
                "Funcionalidade em desenvolvimento.\n\n" +
                "Em breve será possível cancelar poupanças.", 
                "Informação", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}