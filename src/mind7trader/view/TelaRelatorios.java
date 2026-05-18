package view;

import control.GestaoClienteController;
import control.GestaoGrupoController;
import control.GestaoPoupancaController;
import control.GestaoEmprestimoController;
import model.Cliente;
import model.Emprestimo;

import javax.swing.*;
import java.awt.*;

public class TelaRelatorios extends JPanel {

    private GestaoClienteController clienteController;
    private GestaoGrupoController grupoController;
    private GestaoPoupancaController poupancaController;
    private GestaoEmprestimoController emprestimoController;

    public TelaRelatorios() {
        clienteController = new GestaoClienteController();
        grupoController = new GestaoGrupoController();
        poupancaController = new GestaoPoupancaController();
        emprestimoController = new GestaoEmprestimoController();
        initComponents();
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

        // Cards de estatísticas - COM DADOS REAIS
        JPanel statsPanel = new JPanel(new GridLayout(2, 3, 15, 15));
        statsPanel.setBackground(new Color(240, 248, 245));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        // Buscar dados reais
        int totalClientes = clienteController.listarTodosClientes().size();
        int totalGrupos = grupoController.listarTodosGruposAtivos().size();
        int totalPoupancas = poupancaController.listarPoupancasAtivas().size();
        
        // Calcular total de empréstimos ativos
        java.util.List<Emprestimo> emprestimos = emprestimoController.listarTodosEmprestimos();
        int totalEmprestimosAtivos = 0;
        if (emprestimos != null) {
            for (Emprestimo e : emprestimos) {
                String status = e.getStatus().toString();
                if (status.equals("ATIVO") || status.equals("PENDENTE")) {
                    totalEmprestimosAtivos++;
                }
            }
        }
        
        // Calcular saldo total dos clientes
        java.util.List<Cliente> clientes = clienteController.listarTodosClientes();
        double saldoTotal = 0;
        if (clientes != null) {
            for (Cliente c : clientes) {
                saldoTotal += c.getSaldo();
            }
        }
        
        statsPanel.add(createStatCard(" Total Clientes", String.valueOf(totalClientes), new Color(0, 153, 102)));
        statsPanel.add(createStatCard(" Total Grupos", String.valueOf(totalGrupos), new Color(70, 130, 180)));
        statsPanel.add(createStatCard(" Poupanças Ativas", String.valueOf(totalPoupancas), new Color(255, 140, 0)));
        statsPanel.add(createStatCard(" Empréstimos Ativos", String.valueOf(totalEmprestimosAtivos), new Color(150, 100, 200)));
        statsPanel.add(createStatCard(" Saldo Total Clientes", String.format("%,.2f MT", saldoTotal), new Color(200, 100, 50)));
        statsPanel.add(createStatCard(" Taxa de Juros", "20% ao mês", new Color(0, 120, 100)));

        add(statsPanel, BorderLayout.CENTER);

        // Rodapé com informações
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(new Color(240, 248, 245));
        footerPanel.setBorder(BorderFactory.createTitledBorder("Informações do Sistema"));
        
        JTextArea infoArea = new JTextArea(8, 50);
        infoArea.setEditable(false);
        infoArea.setBackground(new Color(240, 248, 245));
        infoArea.setFont(new Font("Arial", Font.PLAIN, 12));
        infoArea.setText(
            "=== REGRAS DO SISTEMA ===\n\n" +
            "1. Poupança: Taxa fixa de 50% sobre o valor investido\n" +
            "2. Empréstimo: Valor não pode exceder soma das poupanças dos últimos 2 meses\n" +
            "3. Juros de empréstimo: 20% ao mês (juros compostos em caso de atraso)\n" +
            "4. Distribuição de lucros: 50% empresa, 50% membros (proporcional ao saldo)\n" +
            "5. Ciclos disponíveis: 6, 9 e 12 meses\n" +
            "6. Períodos: SEMANAL e MENSAL (subgrupos separados)\n\n" +
            "____________________________________________________\n" +
            "Mind7Trader System - Gestão de Poupanças e Empréstimos"
        );
        
        JScrollPane scrollInfo = new JScrollPane(infoArea);
        scrollInfo.setBorder(null);
        footerPanel.add(scrollInfo);
        add(footerPanel, BorderLayout.SOUTH);
    }

    private JPanel createStatCard(String titulo, String valor, Color cor) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(cor, 2),
            BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));
        card.setPreferredSize(new Dimension(150, 100));

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 14));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitulo.setForeground(cor);

        JLabel lblValor = new JLabel(valor);
        lblValor.setFont(new Font("Arial", Font.BOLD, 24));
        lblValor.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblValor.setForeground(new Color(50, 50, 50));

        card.add(lblTitulo);
        card.add(Box.createRigidArea(new Dimension(0, 10)));
        card.add(lblValor);

        return card;
    }
}