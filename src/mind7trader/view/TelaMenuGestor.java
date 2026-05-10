/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
//package mind7trader.view;
package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class TelaMenuGestor extends JFrame {

    private JPanel contentPane;
    private JPanel headerPanel;
    private JPanel cardsPanel;
    private CardLayout cardLayout;

    public TelaMenuGestor() {
        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        setTitle("Mind7Trader - Painel do Gestor");
        setSize(1200, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Painel principal
        contentPane = new JPanel(new BorderLayout());
        contentPane.setBackground(new Color(240, 248, 245));
        setContentPane(contentPane);

        // HEADER
        headerPanel = createHeaderPanel();
        contentPane.add(headerPanel, BorderLayout.NORTH);

        // MENU LATERAL
        JPanel sideMenu = createSideMenu();
        contentPane.add(sideMenu, BorderLayout.WEST);

        // CARDS (conteúdo principal)
        cardLayout = new CardLayout();
        cardsPanel = new JPanel(cardLayout);
        cardsPanel.setBackground(new Color(240, 248, 245));
        cardsPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Adicionar telas aos cards
        cardsPanel.add(new TelaListaClientes(), "clientes");
        cardsPanel.add(new TelaGestaoGrupos(), "grupos");
        cardsPanel.add(new TelaGestaoPoupancas(), "poupancas");
        cardsPanel.add(new TelaGestaoEmprestimos(), "emprestimos");
        cardsPanel.add(new TelaRelatorios(), "relatorios");

        contentPane.add(cardsPanel, BorderLayout.CENTER);

        // Mostrar tela inicial
        cardLayout.show(cardsPanel, "clientes");
    }

    private JPanel createHeaderPanel() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(20, 40, 80));
        header.setPreferredSize(new Dimension(getWidth(), 80));
        header.setBorder(new EmptyBorder(10, 20, 10, 20));

        JLabel title = new JLabel("MIND7TRADER");
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setForeground(new Color(0, 153, 102));
        header.add(title, BorderLayout.WEST);

        JLabel subtitle = new JLabel("Painel de Controlo do Gestor");
        subtitle.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitle.setForeground(Color.LIGHT_GRAY);
        header.add(subtitle, BorderLayout.EAST);

        return header;
    }

    private JPanel createSideMenu() {
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBackground(new Color(30, 50, 90));
        menuPanel.setPreferredSize(new Dimension(220, getHeight()));
        menuPanel.setBorder(new EmptyBorder(20, 10, 20, 10));

        // Título do menu
        JLabel menuTitle = new JLabel("MENU PRINCIPAL");
        menuTitle.setFont(new Font("Arial", Font.BOLD, 14));
        menuTitle.setForeground(new Color(0, 153, 102));
        menuTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuPanel.add(menuTitle);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Botões do menu
        String[] opcoes = {" Clientes", " Grupos", " Poupanças", " Empréstimos", " Relatórios"};
        String[] cards = {"clientes", "grupos", "poupancas", "emprestimos", "relatorios"};

        for (int i = 0; i < opcoes.length; i++) {
            JButton btn = createMenuButton(opcoes[i]);
            final String cardName = cards[i];
            btn.addActionListener(e -> cardLayout.show(cardsPanel, cardName));
            menuPanel.add(btn);
            menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        menuPanel.add(Box.createVerticalGlue());

        // Botão Sair
        JButton btnSair = createMenuButton("🚪 Sair do Sistema");
        btnSair.setBackground(new Color(200, 60, 60));
        btnSair.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(null, "Deseja sair do sistema?", "Sair", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                dispose();
                new TelaLogin();
            }
        });
        menuPanel.add(btnSair);

        return menuPanel;
    }

    private JButton createMenuButton(String text) {
        JButton btn = new JButton(text);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(180, 45));
        btn.setPreferredSize(new Dimension(180, 45));
        btn.setBackground(new Color(50, 70, 110));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 13));
        btn.setFocusPainted(false);
        btn.setBorder(new LineBorder(new Color(0, 153, 102), 1, true));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
}