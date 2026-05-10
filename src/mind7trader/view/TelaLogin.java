/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import control.LoginGestor;
import control.GestaoClienteController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaLogin extends JFrame {

    private JLabel titulo;
    private JLabel lblUser;
    private JLabel lblSenha;
    private JTextField txtUser;
    private JPasswordField txtSenha;
    private JButton btnEntrar;
    private JButton btnSair;
    private JButton btnLoginCliente;

    public TelaLogin() {
        initComponents();
        setupListeners();
        setVisible(true);
    }

    private void initComponents() {
        setTitle("Mind7Trader - Sistema de Gestão");
        setSize(550, 450);
        setLayout(null);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(20, 40, 80));
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Título
        titulo = new JLabel("MIND7TRADER");
        titulo.setBounds(150, 20, 300, 50);
        titulo.setFont(new Font("Arial", Font.BOLD, 32));
        titulo.setForeground(new Color(0, 153, 102));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        add(titulo);

        // Subtítulo
        JLabel lblSubtitulo = new JLabel("Sistema de Gestão de Poupanças e Empréstimos");
        lblSubtitulo.setBounds(100, 70, 350, 25);
        lblSubtitulo.setFont(new Font("Arial", Font.PLAIN, 14));
        lblSubtitulo.setForeground(Color.LIGHT_GRAY);
        lblSubtitulo.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblSubtitulo);

        // Linha decorativa
        JSeparator separator = new JSeparator();
        separator.setBounds(50, 100, 450, 2);
        separator.setForeground(new Color(0, 153, 102));
        add(separator);

        // Label Usuário
        lblUser = new JLabel("Usuário Gestor");
        lblUser.setBounds(80, 130, 150, 30);
        lblUser.setFont(new Font("Arial", Font.BOLD, 14));
        lblUser.setForeground(Color.WHITE);
        add(lblUser);

        // Campo Usuário
        txtUser = new JTextField();
        txtUser.setBounds(80, 160, 250, 35);
        txtUser.setFont(new Font("Arial", Font.PLAIN, 14));
        txtUser.setBackground(new Color(245, 245, 245));
        add(txtUser);

        // Label Senha
        lblSenha = new JLabel("Senha");
        lblSenha.setBounds(80, 210, 150, 30);
        lblSenha.setFont(new Font("Arial", Font.BOLD, 14));
        lblSenha.setForeground(Color.WHITE);
        add(lblSenha);

        // Campo Senha
        txtSenha = new JPasswordField();
        txtSenha.setBounds(80, 240, 250, 35);
        txtSenha.setFont(new Font("Arial", Font.PLAIN, 14));
        txtSenha.setBackground(new Color(245, 245, 245));
        add(txtSenha);

        // Botão Entrar
        btnEntrar = new JButton("ENTRAR");
        btnEntrar.setBounds(80, 310, 120, 40);
        btnEntrar.setBackground(new Color(0, 153, 102));
        btnEntrar.setForeground(Color.WHITE);
        btnEntrar.setFont(new Font("Arial", Font.BOLD, 14));
        btnEntrar.setFocusPainted(false);
        btnEntrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(btnEntrar);

        // Botão Sair
        btnSair = new JButton("SAIR");
        btnSair.setBounds(210, 310, 120, 40);
        btnSair.setBackground(new Color(200, 60, 60));
        btnSair.setForeground(Color.WHITE);
        btnSair.setFont(new Font("Arial", Font.BOLD, 14));
        btnSair.setFocusPainted(false);
        btnSair.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(btnSair);

        // Botão Login Cliente
        /*btnLoginCliente = new JButton("Área do Cliente");
        btnLoginCliente.setBounds(350, 310, 150, 40);
        btnLoginCliente.setBackground(new Color(70, 70, 100));
        btnLoginCliente.setForeground(Color.WHITE);
        btnLoginCliente.setFont(new Font("Arial", Font.BOLD, 12));
        btnLoginCliente.setFocusPainted(false);
        btnLoginCliente.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(btnLoginCliente); */

        // Rodapé
        JLabel lblFooter = new JLabel("© 2026 Mind7Trader - Todos os direitos reservados");
        lblFooter.setBounds(100, 380, 350, 20);
        lblFooter.setFont(new Font("Arial", Font.PLAIN, 10));
        lblFooter.setForeground(Color.GRAY);
        lblFooter.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblFooter);
    }

    private void setupListeners() {
        btnEntrar.addActionListener(e -> {
            LoginGestor control = new LoginGestor();
            String user = txtUser.getText();
            String senha = new String(txtSenha.getPassword());

            if (control.autenticar(user, senha)) {
                JOptionPane.showMessageDialog(null, "Login realizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                new TelaMenuGestor();
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Usuário ou senha incorretos!", "Erro", JOptionPane.ERROR_MESSAGE);
                txtSenha.setText("");
            }
        });

        btnSair.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(null, "Deseja realmente sair do sistema?", "Sair", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });

       /*  btnLoginCliente.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Área do cliente em desenvolvimento.\nUse o login do gestor por enquanto.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
        });*/
    }
}