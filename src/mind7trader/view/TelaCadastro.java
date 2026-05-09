/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view; 
import model.Cliente;
import util.Ficheiro;
import control.LoginCliente;
import control.LoginGestor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Year;
import java.util.Random;

public class TelaCadastro extends JFrame {

    JLabel lblNome;
    JLabel lblBI;
    JLabel lblTelefone;
    JLabel lblEmail;
    JLabel lblMorada;
    JLabel lblSenha;

    JTextField txtNome;
    JTextField txtBI;
    JTextField txtTelefone;
    JTextField txtEmail;
    JTextField txtMorada;
    JPasswordField txtSenha;

    JButton btnSalvar;

    public TelaCadastro() {

        setTitle("Cadastro Cliente");
        setSize(600,500);
        setLayout(null);
        setLocationRelativeTo(null);

        lblNome = new JLabel("Nome Completo");
        lblNome.setBounds(50,50,120,30);
        add(lblNome);

        txtNome = new JTextField();
        txtNome.setBounds(200,50,250,30);
        add(txtNome);

        lblBI = new JLabel("BI");
        lblBI.setBounds(50,100,120,30);
        add(lblBI);

        txtBI = new JTextField();
        txtBI.setBounds(200,100,250,30);
        add(txtBI);

        lblTelefone = new JLabel("Telefone");
        lblTelefone.setBounds(50,150,120,30);
        add(lblTelefone);

        txtTelefone = new JTextField();
        txtTelefone.setBounds(200,150,250,30);
        add(txtTelefone);

        lblEmail = new JLabel("Email");
        lblEmail.setBounds(50,200,120,30);
        add(lblEmail);

        txtEmail = new JTextField();
        txtEmail.setBounds(200,200,250,30);
        add(txtEmail);

        lblMorada = new JLabel("Morada");
        lblMorada.setBounds(50,250,120,30);
        add(lblMorada);

        txtMorada = new JTextField();
        txtMorada.setBounds(200,250,250,30);
        add(txtMorada);

        lblSenha = new JLabel("Senha");
        lblSenha.setBounds(50,300,120,30);
        add(lblSenha);

        txtSenha = new JPasswordField();
        txtSenha.setBounds(200,300,250,30);
        add(txtSenha);

        btnSalvar = new JButton("Salvar Cliente");
        btnSalvar.setBounds(200,380,180,40);
        btnSalvar.setBackground(new Color(0,153,102));
        btnSalvar.setForeground(Color.WHITE);
        add(btnSalvar);

        btnSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                LoginCliente control = new LoginCliente(); // mudado de TelaCadastro para LoginCliente, bug fixado

                if(!control.validarTelefone(txtTelefone.getText())) {

                    JOptionPane.showMessageDialog(null,
                            "Número inválido");

                    return;
                }

                if(!control.validarEmail(txtEmail.getText())) {

                    JOptionPane.showMessageDialog(null,
                            "Email inválido");

                    return;
                }

                String conta = gerarNumeroConta();

                Cliente cliente = new Cliente(
                        conta,
                        txtNome.getText(),
                        txtBI.getText(),
                        txtTelefone.getText(),
                        txtEmail.getText(),
                        txtMorada.getText(),
                        0,
                        new String(txtSenha.getPassword()), // mudado de getText() para getPassword()
                        true
                );

                control.salvar(cliente);

                JOptionPane.showMessageDialog(null,
                        "Cliente registado com sucesso\nConta: " + conta);
            }
        });

        setVisible(true);
    }

    public String gerarNumeroConta() {

        int ano = Year.now().getValue();

        Random random = new Random();

        int numero = random.nextInt(9000) + 1000;

        return ano + String.valueOf(numero);
    }
}