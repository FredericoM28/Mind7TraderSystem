/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;
import java.io.*;
import java.util.ArrayList;
import model.Cliente;

public class Ficheiro {

    private static final String FICHEIRO = "clientes.dat";

    public static void salvarCliente(model.Cliente cliente) {

        ArrayList<Cliente> lista = listarClientes();

        lista.add(cliente);

        try {

            ObjectOutputStream out = new ObjectOutputStream(
                    new FileOutputStream(FICHEIRO));

            out.writeObject(lista);
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Cliente> listarClientes() {

        ArrayList<Cliente> lista = new ArrayList<>();

        try {

            ObjectInputStream in = new ObjectInputStream(
                    new FileInputStream(FICHEIRO));

            lista = (ArrayList<Cliente>) in.readObject();

            in.close();

        } catch (Exception e) {

        }

        return lista;
    }
}
