package util;

import model.Cliente;
import model.Grupo;
import model.Poupanca;
import model.Emprestimo;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Ficheiro {
    
    private static final String CLIENTES_FILE = "clientes.dat";
    private static final String GRUPOS_FILE = "grupos.dat";
    private static final String POUPANCAS_FILE = "poupancas.dat";
    private static final String EMPRESTIMOS_FILE = "emprestimos.dat";
    
    // Cliente
    public static void salvarCliente(Cliente cliente) {
        List<Cliente> clientes = carregarClientes();
        if (clientes == null) {
            clientes = new ArrayList<>();
        }
        clientes.add(cliente);
        salvarListaClientes(clientes);
    }
    
    public static List<Cliente> carregarClientes() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(CLIENTES_FILE))) {
            return (List<Cliente>) ois.readObject();
        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static void salvarListaClientes(List<Cliente> clientes) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CLIENTES_FILE))) {
            oos.writeObject(clientes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // Grupo
    public static void salvarGrupo(Grupo grupo) {
        List<Grupo> grupos = carregarGrupos();
        if (grupos == null) {
            grupos = new ArrayList<>();
        }
        grupos.add(grupo);
        salvarListaGrupos(grupos);
    }
    
    public static List<Grupo> carregarGrupos() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(GRUPOS_FILE))) {
            return (List<Grupo>) ois.readObject();
        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static void salvarListaGrupos(List<Grupo> grupos) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(GRUPOS_FILE))) {
            oos.writeObject(grupos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // Poupança
    public static void salvarPoupanca(Poupanca poupanca) {
        List<Poupanca> poupancas = carregarPoupancas();
        if (poupancas == null) {
            poupancas = new ArrayList<>();
        }
        poupancas.add(poupanca);
        salvarListaPoupancas(poupancas);
    }
    
    public static List<Poupanca> carregarPoupancas() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(POUPANCAS_FILE))) {
            return (List<Poupanca>) ois.readObject();
        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static void salvarListaPoupancas(List<Poupanca> poupancas) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(POUPANCAS_FILE))) {
            oos.writeObject(poupancas);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // Empréstimo
    public static void salvarEmprestimo(Emprestimo emprestimo) {
        List<Emprestimo> emprestimos = carregarEmprestimos();
        if (emprestimos == null) {
            emprestimos = new ArrayList<>();
        }
        emprestimos.add(emprestimo);
        salvarListaEmprestimos(emprestimos);
    }
    
    public static List<Emprestimo> carregarEmprestimos() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(EMPRESTIMOS_FILE))) {
            return (List<Emprestimo>) ois.readObject();
        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static void salvarListaEmprestimos(List<Emprestimo> emprestimos) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(EMPRESTIMOS_FILE))) {
            oos.writeObject(emprestimos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}