/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
//package mind7trader.view;
package view;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaMenuGestor extends JFrame {

    JButton btnCadastrar;

    public TelaMenuGestor() {

        setTitle("Menu Gestor");
        setSize(500,400);
        setLayout(null);
        setLocationRelativeTo(null);

        getContentPane().setBackground(new Color(240,240,240));

        btnCadastrar = new JButton("Cadastrar Cliente");
        btnCadastrar.setBounds(130,100,220,40);
        add(btnCadastrar);

        btnCadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                new TelaCadastro(); // bug fixado, mudado de TelaCadastroCliente para TelaCadastro
            }
        });

        setVisible(true);
    }
}