package control;

import model.Poupanca;
import model.Poupanca.TipoCiclo;
import model.Poupanca.TipoPeriodo;
import model.Cliente;
import util.Ficheiro;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GestaoPoupancaController {
    
    private List<Poupanca> poupancas;
    private GestaoClienteController gestaoCliente;
    private GestaoGrupoController gestaoGrupo;
    
    public GestaoPoupancaController() {
        carregarPoupancas();
        this.gestaoCliente = new GestaoClienteController();
        this.gestaoGrupo = new GestaoGrupoController();
    }
    
    private void carregarPoupancas() {
        List<Poupanca> carregados = Ficheiro.carregarPoupancas();
        if (carregados == null) {
            this.poupancas = new ArrayList<>();
        } else {
            this.poupancas = carregados;
        }
    }
    
    private void salvarPoupancas() {
        Ficheiro.salvarListaPoupancas(poupancas);
    }
    
    // CREATE - SEM verificação de saldo! (O cliente NÃO precisa ter saldo prévio)
    public Poupanca criarPoupanca(String numeroContaCliente, String idGrupo, 
                                   double valorInvestido, TipoCiclo ciclo, TipoPeriodo periodo) {
        // Validar se cliente existe
        Cliente cliente = gestaoCliente.buscarClientePorNumeroConta(numeroContaCliente);
        if (cliente == null) {
            System.out.println("Cliente não encontrado: " + numeroContaCliente);
            return null;
        }
        
        // NÃO verificar saldo! O cliente NÃO precisa ter dinheiro para começar uma poupança
        // A poupança é um compromisso de investimento futuro
        
        String id = UUID.randomUUID().toString();
        Poupanca poupanca = new Poupanca(id, numeroContaCliente, idGrupo, valorInvestido, ciclo, periodo);
        poupancas.add(poupanca);
        salvarPoupancas();
        
        // Adicionar ao grupo
        if (idGrupo != null && !idGrupo.isEmpty()) {
            gestaoGrupo.adicionarMembroAoGrupo(idGrupo, numeroContaCliente);
            gestaoGrupo.atualizarSaldoGrupo(idGrupo, valorInvestido);
        }
        
        System.out.println("Poupança criada: ID=" + id + ", Cliente=" + numeroContaCliente + ", Valor=" + valorInvestido);
        
        return poupanca;
    }
    
    // READ
    public Poupanca buscarPoupancaPorId(String id) {
        for (Poupanca poupanca : poupancas) {
            if (poupanca.getId().equals(id)) {
                return poupanca;
            }
        }
        return null;
    }
    
    public List<Poupanca> listarPoupancasPorCliente(String numeroContaCliente) {
        List<Poupanca> resultado = new ArrayList<>();
        for (Poupanca poupanca : poupancas) {
            if (poupanca.getNumeroContaCliente().equals(numeroContaCliente)) {
                resultado.add(poupanca);
            }
        }
        return resultado;
    }
    
    public List<Poupanca> listarPoupancasPorGrupo(String idGrupo) {
        List<Poupanca> resultado = new ArrayList<>();
        for (Poupanca poupanca : poupancas) {
            if (poupanca.getIdGrupo() != null && poupanca.getIdGrupo().equals(idGrupo)) {
                resultado.add(poupanca);
            }
        }
        return resultado;
    }
    
    public List<Poupanca> listarPoupancasAtivas() {
        List<Poupanca> ativas = new ArrayList<>();
        for (Poupanca poupanca : poupancas) {
            if (poupanca.getStatus() == Poupanca.StatusPoupanca.ATIVA) {
                ativas.add(poupanca);
            }
        }
        return ativas;
    }
    
    public List<Poupanca> listarTodasPoupancas() {
        return new ArrayList<>(poupancas);
    }
    
    // UPDATE - CONCLUIR poupança
    public boolean concluirPoupanca(String idPoupanca) {
        Poupanca poupanca = buscarPoupancaPorId(idPoupanca);
        if (poupanca == null) {
            System.out.println("Poupança não encontrada: " + idPoupanca);
            return false;
        }
        
        if (poupanca.getStatus() != Poupanca.StatusPoupanca.ATIVA) {
            System.out.println("Poupança não está ativa: " + poupanca.getStatus());
            return false;
        }
        
        // Verificar se já atingiu a data de conclusão
        if (!poupanca.isConcluida()) {
            System.out.println("Poupança ainda não atingiu a data de conclusão");
            return false;
        }
        
        // Mudar status para CONCLUIDA
        poupanca.setStatus(Poupanca.StatusPoupanca.CONCLUIDA);
        
        // Adicionar valor total + juros ao saldo do cliente
        Cliente cliente = gestaoCliente.buscarClientePorNumeroConta(poupanca.getNumeroContaCliente());
        if (cliente != null) {
            double valorReceber = poupanca.getValorTotalComJuros();
            cliente.adicionarSaldo(valorReceber);
            gestaoCliente.atualizarCliente(cliente);
            System.out.println("Poupança concluída: Cliente " + cliente.getNumeroConta() + 
                             " recebeu " + valorReceber);
        }
        
        salvarPoupancas();
        return true;
    }
    
    // UPDATE - CANCELAR poupança
    public boolean cancelarPoupanca(String idPoupanca) {
        Poupanca poupanca = buscarPoupancaPorId(idPoupanca);
        if (poupanca == null) {
            System.out.println("Poupança não encontrada: " + idPoupanca);
            return false;
        }
        
        if (poupanca.getStatus() != Poupanca.StatusPoupanca.ATIVA) {
            System.out.println("Poupança não está ativa: " + poupanca.getStatus());
            return false;
        }
        
        // Mudar status para CANCELADA
        poupanca.setStatus(Poupanca.StatusPoupanca.CANCELADA);
        
        // NÃO devolve nada ao cliente porque ele nunca pagou nada
        // O cancelamento apenas remove a poupança do sistema
        
        // Remover do saldo do grupo
        if (poupanca.getIdGrupo() != null && !poupanca.getIdGrupo().isEmpty()) {
            gestaoGrupo.atualizarSaldoGrupo(poupanca.getIdGrupo(), -poupanca.getValorInvestido());
        }
        
        salvarPoupancas();
        System.out.println("Poupança cancelada: " + idPoupanca);
        return true;
    }
    
    // RELATÓRIOS
    public double getTotalInvestidoPorCliente(String numeroContaCliente) {
        double total = 0;
        for (Poupanca poupanca : listarPoupancasPorCliente(numeroContaCliente)) {
            if (poupanca.getStatus() == Poupanca.StatusPoupanca.ATIVA) {
                total += poupanca.getValorInvestido();
            }
        }
        return total;
    }
    
    public double getMaiorSaldoPoupancaPorGrupo(String idGrupo) {
        double maiorSaldo = 0;
        for (Poupanca poupanca : listarPoupancasPorGrupo(idGrupo)) {
            if (poupanca.getStatus() == Poupanca.StatusPoupanca.ATIVA) {
                if (poupanca.getValorInvestido() > maiorSaldo) {
                    maiorSaldo = poupanca.getValorInvestido();
                }
            }
        }
        return maiorSaldo;
    }
}