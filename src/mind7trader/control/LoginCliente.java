/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import model.Cliente;
import util.Ficheiro;

public class LoginCliente {

    public void salvar(Cliente cliente) {

        Ficheiro.salvarCliente(cliente);
    }

    public boolean validarTelefone(String telefone) {
 
        return telefone.matches("^(84|85|86|87)[0-9]{7}$");
    }

    public boolean validarEmail(String email) {

        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }
}