package control;

import model.Cliente;
import util.Ficheiro;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GestaoClienteController {
    
    private List<Cliente> clientes;
    
    public GestaoClienteController() {
        carregarClientes();
    }
    
    private void carregarClientes() {
        List<Cliente> carregados = Ficheiro.carregarClientes();
        if (carregados == null) {
            this.clientes = new ArrayList<>();
        } else {
            this.clientes = carregados;
        }
    }
    
    private void salvarClientes() {
        Ficheiro.salvarListaClientes(clientes);
    }
    
    // CREATE
    public Cliente criarCliente(String nome, String bi, String telefone, 
                                String email, String morada, String senha) {
        String numeroConta = gerarNumeroConta();
        Cliente cliente = new Cliente(numeroConta, nome, bi, telefone, email, morada, 0, senha, true, null, null);
        clientes.add(cliente);
        salvarClientes();
        return cliente;
    }
    
    // READ
    public Cliente buscarClientePorNumeroConta(String numeroConta) {
        for (Cliente cliente : clientes) {
            if (cliente.getNumeroConta().equals(numeroConta) && cliente.isAtivo()) {
                return cliente;
            }
        }
        return null;
    }
    
    public Cliente buscarClientePorBI(String bi) {
        for (Cliente cliente : clientes) {
            if (cliente.getBi().equals(bi) && cliente.isAtivo()) {
                return cliente;
            }
        }
        return null;
    }
    
    public List<Cliente> listarTodosClientes() {
        List<Cliente> ativos = new ArrayList<>();
        for (Cliente cliente : clientes) {
            if (cliente.isAtivo()) {
                ativos.add(cliente);
            }
        }
        return ativos;
    }
    
    // UPDATE (NOVO MÉTODO)
    public boolean atualizarCliente(Cliente clienteAtualizado) {
        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getNumeroConta().equals(clienteAtualizado.getNumeroConta())) {
                clientes.set(i, clienteAtualizado);
                salvarClientes();
                return true;
            }
        }
        return false;
    }
    
    public boolean atualizarCliente(String numeroConta, String nome, String telefone, 
                                    String email, String morada) {
        Cliente cliente = buscarClientePorNumeroConta(numeroConta);
        if (cliente != null) {
            cliente.setNome(nome);
            cliente.setTelefone(telefone);
            cliente.setEmail(email);
            cliente.setMorada(morada);
            salvarClientes();
            return true;
        }
        return false;
    }
    
    // DELETE (soft delete)
    public boolean desativarCliente(String numeroConta) {
        Cliente cliente = buscarClientePorNumeroConta(numeroConta);
        if (cliente != null) {
            cliente.setAtivo(false);
            salvarClientes();
            return true;
        }
        return false;
    }
    
    // VALIDAÇÕES
    public boolean validarTelefone(String telefone) {
        return telefone.matches("^(84|85|86|87)[0-9]{7}$");
    }
    
    public boolean validarEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }
    
    public boolean autenticarCliente(String numeroConta, String senha) {
        Cliente cliente = buscarClientePorNumeroConta(numeroConta);
        return cliente != null && cliente.getSenha().equals(senha) && cliente.isAtivo();
    }
    
    private String gerarNumeroConta() {
        return String.valueOf(System.currentTimeMillis()).substring(4);
    }
}