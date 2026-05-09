/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import control.LoginGestor; 

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener;

public class TelaLogin extends JFrame {

    JLabel titulo;
    JLabel lblUser;
    JLabel lblSenha;

    JTextField txtUser;
    JPasswordField txtSenha;

    JButton btnEntrar;

    public TelaLogin() {

        setTitle("Mind7Trader - Login");
        setSize(500, 350);
        setLayout(null);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(20,40,80));

        titulo = new JLabel("MIND7TRADER");
        titulo.setBounds(150,20,300,40);
        titulo.setFont(new Font("Arial", Font.BOLD, 28));
        titulo.setForeground(Color.WHITE);
        add(titulo);

        lblUser = new JLabel("Usuário");
        lblUser.setBounds(70,100,100,30);
        lblUser.setForeground(Color.WHITE);
        add(lblUser);

        txtUser = new JTextField();
        txtUser.setBounds(170,100,200,30);
        add(txtUser);

        lblSenha = new JLabel("Senha");
        lblSenha.setBounds(70,150,100,30);
        lblSenha.setForeground(Color.WHITE);
        add(lblSenha);

        txtSenha = new JPasswordField();
        txtSenha.setBounds(170,150,200,30);
        add(txtSenha);

        btnEntrar = new JButton("Entrar");
        btnEntrar.setBounds(170,220,120,35);
        btnEntrar.setBackground(new Color(0,153,102));
        btnEntrar.setForeground(Color.WHITE);
        add(btnEntrar);

        btnEntrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                LoginGestor control = new LoginGestor(); // bug fixado, mudado de TelaLogin para LoginGestor

                String user = txtUser.getText();
                String senha = new String(txtSenha.getPassword());

                 if(control.autenticar(user, senha)){  
  
                    JOptionPane.showMessageDialog(null,
                            "Login realizado com sucesso");

                    new TelaMenuGestor();
                    dispose();

                } else {

                    JOptionPane.showMessageDialog(null,
                            "Usuário ou senha incorretos");
                }
            }
        });

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

   
}