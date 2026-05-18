package view;

import control.GestaoClienteController;
import control.GestaoGrupoController;
import control.GestaoPoupancaController;
import control.GestaoEmprestimoController;
import model.Cliente;
import model.Grupo;
import model.Poupanca;
import model.Emprestimo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TelaRelatorios extends JPanel {

    private GestaoClienteController clienteController;
    private GestaoGrupoController grupoController;
    private GestaoPoupancaController poupancaController;
    private GestaoEmprestimoController emprestimoController;
    
    private JTabbedPane tabbedPane;
    private JTable tableClientes;
    private JTable tableGrupos;
    private JTable tablePoupancas;
    private JTable tableEmprestimos;

    public TelaRelatorios() {
        clienteController = new GestaoClienteController();
        grupoController = new GestaoGrupoController();
        poupancaController = new GestaoPoupancaController();
        emprestimoController = new GestaoEmprestimoController();
        initComponents();
        carregarDados();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(240, 248, 245));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Título
        JLabel titulo = new JLabel("Relatórios e Estatísticas");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(new Color(20, 40, 80));
        add(titulo, BorderLayout.NORTH);

        // Painel de Cards (Estatísticas Rápidas)
        JPanel statsPanel = criarPainelCards();
        add(statsPanel, BorderLayout.CENTER);

        // Abas para detalhes
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 12));
        tabbedPane.setBackground(new Color(240, 248, 245));
        
        // Aba de Clientes
        tabbedPane.addTab("📋 Clientes", criarPainelClientes());
        
        // Aba de Grupos
        tabbedPane.addTab("👥 Grupos", criarPainelGrupos());
        
        // Aba de Poupanças
        tabbedPane.addTab("💰 Poupanças", criarPainelPoupancas());
        
        // Aba de Empréstimos
        tabbedPane.addTab("📊 Empréstimos", criarPainelEmprestimos());
        
        // Aba de Regras do Sistema
        tabbedPane.addTab("⚙️ Regras do Sistema", criarPainelRegras());
        
        add(tabbedPane, BorderLayout.SOUTH);
    }

    private JPanel criarPainelCards() {
        JPanel statsPanel = new JPanel(new GridLayout(2, 4, 15, 15));
        statsPanel.setBackground(new Color(240, 248, 245));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));
        
        // Buscar dados reais
        List<Cliente> clientes = clienteController.listarTodosClientes();
        List<Grupo> grupos = grupoController.listarTodosGrupos();
        List<Poupanca> poupancas = poupancaController.listarTodasPoupancas();
        List<Emprestimo> emprestimos = emprestimoController.listarTodosEmprestimos();
        
        int totalClientes = clientes.size();
        int totalGruposAtivos = grupoController.listarTodosGruposAtivos().size();
        int totalGruposFinalizados = 0;
        for (Grupo g : grupos) {
            if (g.getStatus().toString().equals("FINALIZADO")) totalGruposFinalizados++;
        }
        
        int poupancasAtivas = 0;
        int poupancasConcluidas = 0;
        double totalInvestido = 0;
        for (Poupanca p : poupancas) {
            if (p.getStatus().toString().equals("ATIVA")) {
                poupancasAtivas++;
                totalInvestido += p.getValorInvestido();
            } else if (p.getStatus().toString().equals("CONCLUIDA")) {
                poupancasConcluidas++;
            }
        }
        
        int emprestimosAtivos = 0;
        int emprestimosPagos = 0;
        double totalEmprestado = 0;
        for (Emprestimo e : emprestimos) {
            if (e.getStatus().toString().equals("ATIVO") || e.getStatus().toString().equals("PENDENTE")) {
                emprestimosAtivos++;
                totalEmprestado += e.getValorSolicitado();
            } else if (e.getStatus().toString().equals("PAGO")) {
                emprestimosPagos++;
            }
        }
        
        double saldoTotalClientes = 0;
        for (Cliente c : clientes) {
            saldoTotalClientes += c.getSaldo();
        }
        
        double saldoTotalGrupos = 0;
        for (Grupo g : grupos) {
            saldoTotalGrupos += g.getSaldoTotalGrupo();
        }
        
        // Criar os cards
        statsPanel.add(createStatCard("👥 Clientes Ativos", String.valueOf(totalClientes), new Color(0, 153, 102)));
        statsPanel.add(createStatCard("👥 Grupos (Ativos)", String.valueOf(totalGruposAtivos), new Color(70, 130, 180)));
        statsPanel.add(createStatCard("🏁 Grupos Finalizados", String.valueOf(totalGruposFinalizados), new Color(100, 100, 150)));
        statsPanel.add(createStatCard("💰 Poupanças Ativas", String.valueOf(poupancasAtivas), new Color(255, 140, 0)));
        statsPanel.add(createStatCard("✅ Poupanças Concluídas", String.valueOf(poupancasConcluidas), new Color(0, 120, 80)));
        statsPanel.add(createStatCard("📊 Empréstimos Ativos", String.valueOf(emprestimosAtivos), new Color(200, 100, 50)));
        statsPanel.add(createStatCard("💵 Saldo Total Clientes", String.format("%,.2f MT", saldoTotalClientes), new Color(0, 153, 102)));
        statsPanel.add(createStatCard("🏦 Saldo Total Grupos", String.format("%,.2f MT", saldoTotalGrupos), new Color(70, 130, 180)));
        
        return statsPanel;
    }
    
    private JPanel criarPainelClientes() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 248, 245));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        String[] colunas = {"Nº Conta", "Nome", "BI", "Telefone", "Email", "Saldo (MT)", "Status"};
        DefaultTableModel model = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        List<Cliente> clientes = clienteController.listarTodosClientes();
        for (Cliente c : clientes) {
            model.addRow(new Object[]{
                c.getNumeroConta(),
                c.getNome(),
                c.getBi(),
                c.getTelefone(),
                c.getEmail(),
                String.format("%,.2f", c.getSaldo()),
                c.isAtivo() ? "Ativo" : "Inativo"
            });
        }
        
        tableClientes = new JTable(model);
        tableClientes.setFont(new Font("Arial", Font.PLAIN, 12));
        tableClientes.setRowHeight(25);
        tableClientes.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tableClientes.getTableHeader().setBackground(new Color(20, 40, 80));
        tableClientes.getTableHeader().setForeground(Color.WHITE);
        
        JScrollPane scroll = new JScrollPane(tableClientes);
        scroll.setBorder(BorderFactory.createTitledBorder("Lista de Clientes"));
        panel.add(scroll, BorderLayout.CENTER);
        
        // Resumo
        JLabel lblResumo = new JLabel("Total de clientes: " + clientes.size() + " | Saldo total: " + 
                                      String.format("%,.2f", calcularSaldoTotalClientes()) + " MT");
        lblResumo.setFont(new Font("Arial", Font.BOLD, 12));
        lblResumo.setForeground(new Color(0, 100, 0));
        lblResumo.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        panel.add(lblResumo, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel criarPainelGrupos() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 248, 245));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        String[] colunas = {"ID", "Nome", "Período", "Ciclo", "Membros", "Saldo Total", "Status"};
        DefaultTableModel model = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        List<Grupo> grupos = grupoController.listarTodosGrupos();
        for (Grupo g : grupos) {
            model.addRow(new Object[]{
                g.getId().substring(0, Math.min(8, g.getId().length())) + "...",
                g.getNome(),
                g.getPeriodo(),
                g.getCiclo().toString().replace("_", " "),
                g.getQuantidadeMembros(),
                String.format("%,.2f", g.getSaldoTotalGrupo()),
                g.getStatus()
            });
        }
        
        tableGrupos = new JTable(model);
        tableGrupos.setFont(new Font("Arial", Font.PLAIN, 12));
        tableGrupos.setRowHeight(25);
        tableGrupos.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tableGrupos.getTableHeader().setBackground(new Color(20, 40, 80));
        tableGrupos.getTableHeader().setForeground(Color.WHITE);
        
        JScrollPane scroll = new JScrollPane(tableGrupos);
        scroll.setBorder(BorderFactory.createTitledBorder("Lista de Grupos"));
        panel.add(scroll, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel criarPainelPoupancas() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 248, 245));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        String[] colunas = {"ID", "Cliente", "Valor Investido", "Valor Final", "Data Fim", "Ciclo", "Status"};
        DefaultTableModel model = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        List<Poupanca> poupancas = poupancaController.listarTodasPoupancas();
        double totalInvestido = 0;
        double totalReceber = 0;
        
        for (Poupanca p : poupancas) {
            model.addRow(new Object[]{
                p.getId().substring(0, Math.min(8, p.getId().length())) + "...",
                p.getNumeroContaCliente(),
                String.format("%,.2f", p.getValorInvestido()),
                String.format("%,.2f", p.getValorTotalComJuros()),
                p.getDataFim(),
                p.getCiclo().toString().replace("_", " "),
                p.getStatus()
            });
            if (p.getStatus().toString().equals("ATIVA")) {
                totalInvestido += p.getValorInvestido();
                totalReceber += p.getValorTotalComJuros();
            }
        }
        
        tablePoupancas = new JTable(model);
        tablePoupancas.setFont(new Font("Arial", Font.PLAIN, 12));
        tablePoupancas.setRowHeight(25);
        tablePoupancas.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tablePoupancas.getTableHeader().setBackground(new Color(20, 40, 80));
        tablePoupancas.getTableHeader().setForeground(Color.WHITE);
        
        JScrollPane scroll = new JScrollPane(tablePoupancas);
        scroll.setBorder(BorderFactory.createTitledBorder("Lista de Poupanças"));
        panel.add(scroll, BorderLayout.CENTER);
        
        JLabel lblResumo = new JLabel("💰 Total Investido em Poupanças Ativas: " + String.format("%,.2f", totalInvestido) + 
                                      " MT | 📈 Valor a Receber: " + String.format("%,.2f", totalReceber) + " MT | 💵 Lucro Potencial: " + 
                                      String.format("%,.2f", totalReceber - totalInvestido) + " MT");
        lblResumo.setFont(new Font("Arial", Font.BOLD, 12));
        lblResumo.setForeground(new Color(0, 100, 0));
        lblResumo.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        panel.add(lblResumo, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel criarPainelEmprestimos() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 248, 245));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        String[] colunas = {"ID", "Cliente", "Valor Solicitado", "Valor com Juros", "Dívida Atual", "Vencimento", "Status"};
        DefaultTableModel model = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        List<Emprestimo> emprestimos = emprestimoController.listarTodosEmprestimos();
        double totalSolicitado = 0;
        double totalDivida = 0;
        
        for (Emprestimo e : emprestimos) {
            e.verificarAtraso();
            model.addRow(new Object[]{
                e.getId().substring(0, Math.min(8, e.getId().length())) + "...",
                e.getNumeroContaCliente(),
                String.format("%,.2f", e.getValorSolicitado()),
                String.format("%,.2f", e.getValorComJuros()),
                String.format("%,.2f", e.getValorEmDivida()),
                e.getDataVencimento(),
                e.getStatus()
            });
            if (e.getStatus().toString().equals("ATIVO") || e.getStatus().toString().equals("PENDENTE")) {
                totalSolicitado += e.getValorSolicitado();
                totalDivida += e.getValorEmDivida();
            }
        }
        
        tableEmprestimos = new JTable(model);
        tableEmprestimos.setFont(new Font("Arial", Font.PLAIN, 12));
        tableEmprestimos.setRowHeight(25);
        tableEmprestimos.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tableEmprestimos.getTableHeader().setBackground(new Color(20, 40, 80));
        tableEmprestimos.getTableHeader().setForeground(Color.WHITE);
        
        JScrollPane scroll = new JScrollPane(tableEmprestimos);
        scroll.setBorder(BorderFactory.createTitledBorder("Lista de Empréstimos"));
        panel.add(scroll, BorderLayout.CENTER);
        
        JLabel lblResumo = new JLabel("📊 Total Emprestado: " + String.format("%,.2f", totalSolicitado) + 
                                      " MT | 💰 Dívida Total Ativa: " + String.format("%,.2f", totalDivida) + " MT");
        lblResumo.setFont(new Font("Arial", Font.BOLD, 12));
        lblResumo.setForeground(new Color(200, 100, 0));
        lblResumo.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        panel.add(lblResumo, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel criarPainelRegras() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 248, 245));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JTextArea infoArea = new JTextArea();
        infoArea.setEditable(false);
        infoArea.setBackground(new Color(250, 250, 245));
        infoArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        infoArea.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        infoArea.setText(
            "\n" +
            "╔══════════════════════════════════════════════════════════════════════════════╗\n" +
            "║                         REGRAS DO SISTEMA - MIND7TRADER                       ║\n" +
            "╚══════════════════════════════════════════════════════════════════════════════╝\n\n" +
            "┌─────────────────────────────────────────────────────────────────────────────┐\n" +
            "│ 📌 POUPANÇAS                                                                 │\n" +
            "├─────────────────────────────────────────────────────────────────────────────┤\n" +
            "│  • Taxa fixa de 50% sobre o valor investido                                  │\n" +
            "│  • Exemplo: Investe 500 MT → Recebe 750 MT no final                         │\n" +
            "│  • Ciclos disponíveis: 6 meses, 9 meses, 12 meses                           │\n" +
            "│  • Períodos: SEMANAL ou MENSAL (subgrupos separados)                        │\n" +
            "│  • O cliente NÃO precisa ter saldo prévio para iniciar uma poupança         │\n" +
            "└─────────────────────────────────────────────────────────────────────────────┘\n\n" +
            "┌─────────────────────────────────────────────────────────────────────────────┐\n" +
            "│ 📌 EMPRÉSTIMOS                                                               │\n" +
            "├─────────────────────────────────────────────────────────────────────────────┤\n" +
            "│  • Valor do empréstimo NÃO pode exceder a soma das poupanças dos últimos    │\n" +
            "│    2 meses do cliente                                                        │\n" +
            "│  • Juros: 20% ao mês                                                         │\n" +
            "│  • Pagamento deve ser feito em até 1 mês                                     │\n" +
            "│  • Em caso de atraso: juros compostos de 20% sobre o valor em atraso        │\n" +
            "└─────────────────────────────────────────────────────────────────────────────┘\n\n" +
            "┌─────────────────────────────────────────────────────────────────────────────┐\n" +
            "│ 📌 DISTRIBUIÇÃO DE LUCROS                                                    │\n" +
            "├─────────────────────────────────────────────────────────────────────────────┤\n" +
            "│  • 50% dos juros → Empresa                                                   │\n" +
            "│  • 50% dos juros → Membros do grupo                                          │\n" +
            "│  • Distribuição proporcional ao saldo da poupança de cada membro            │\n" +
            "│  • Quem tem MAIOR saldo na poupança, ganha MAIS                              │\n" +
            "└─────────────────────────────────────────────────────────────────────────────┘\n\n" +
            "┌─────────────────────────────────────────────────────────────────────────────┐\n" +
            "│ 📌 GRUPOS                                                                     │\n" +
            "├─────────────────────────────────────────────────────────────────────────────┤\n" +
            "│  • Grupos SEMANAIS e MENSAL são subgrupos separados                          │\n" +
            "│  • Cada grupo tem um ciclo fixo (6, 9 ou 12 meses)                          │\n" +
            "│  • Gestores e Administradores têm acesso a todos os grupos                  │\n" +
            "└─────────────────────────────────────────────────────────────────────────────┘\n\n" +
            "┌─────────────────────────────────────────────────────────────────────────────┐\n" +
            "│ 📌 CLIENTES                                                                   │\n" +
            "├─────────────────────────────────────────────────────────────────────────────┤\n" +
            "│  • Idade mínima: 18 anos                                                     │\n" +
            "│  • Obrigatório informar um herdeiro                                          │\n" +
            "│  • Saldo inicial = 0 MT                                                      │\n" +
            "│  • Saldo aumenta com conclusão de poupanças ou empréstimos                  │\n" +
            "└─────────────────────────────────────────────────────────────────────────────┘\n\n" +
            "═══════════════════════════════════════════════════════════════════════════════\n" +
            "                    Mind7Trader System - Gestão de Poupanças e Empréstimos       \n" +
            "═══════════════════════════════════════════════════════════════════════════════"
        );
        
        JScrollPane scrollInfo = new JScrollPane(infoArea);
        scrollInfo.setBorder(null);
        panel.add(scrollInfo, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createStatCard(String titulo, String valor, Color cor) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(cor, 2),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        card.setPreferredSize(new Dimension(160, 90));

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 12));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitulo.setForeground(cor);

        JLabel lblValor = new JLabel(valor);
        lblValor.setFont(new Font("Arial", Font.BOLD, 16));
        lblValor.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblValor.setForeground(new Color(50, 50, 50));

        card.add(lblTitulo);
        card.add(Box.createRigidArea(new Dimension(0, 8)));
        card.add(lblValor);

        return card;
    }
    
    private void carregarDados() {
        // Método para atualizar todos os dados (pode ser chamado por um botão)
        // Por enquanto, os dados são carregados na criação
    }
    
    private double calcularSaldoTotalClientes() {
        double total = 0;
        for (Cliente c : clienteController.listarTodosClientes()) {
            total += c.getSaldo();
        }
        return total;
    }
}