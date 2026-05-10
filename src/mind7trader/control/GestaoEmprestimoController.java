package control;

import model.Emprestimo;
import model.Poupanca;
import model.Cliente;
import util.Ficheiro;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GestaoEmprestimoController {
    
    private List<Emprestimo> emprestimos;
    private GestaoClienteController gestaoCliente;
    private GestaoPoupancaController gestaoPoupanca;
    private GestaoGrupoController gestaoGrupo;
    
    public GestaoEmprestimoController() {
        carregarEmprestimos();
        this.gestaoCliente = new GestaoClienteController();
        this.gestaoPoupanca = new GestaoPoupancaController();
        this.gestaoGrupo = new GestaoGrupoController();
    }
    
    private void carregarEmprestimos() {
        List<Emprestimo> carregados = Ficheiro.carregarEmprestimos();
        if (carregados == null) {
            this.emprestimos = new ArrayList<>();
        } else {
            this.emprestimos = carregados;
        }
    }
    
    private void salvarEmprestimos() {
        Ficheiro.salvarListaEmprestimos(emprestimos);
    }
    
    // Método para calcular soma das poupanças dos últimos 2 meses
    private double calcularSomaUltimosDoisMesesPoupanca(String numeroContaCliente) {
        List<Poupanca> poupancasCliente = gestaoPoupanca.listarPoupancasPorCliente(numeroContaCliente);
        double soma = 0;
        int contador = 0;
        
        // Ordenar por data decrescente e pegar as 2 mais recentes
        poupancasCliente.sort((p1, p2) -> p2.getDataInicio().compareTo(p1.getDataInicio()));
        
        for (Poupanca poupanca : poupancasCliente) {
            if (contador < 2 && poupanca.getStatus() == Poupanca.StatusPoupanca.ATIVA) {
                soma += poupanca.getValorInvestido();
                contador++;
            }
        }
        return soma;
    }
    
    // CREATE - com validação da regra de negócio
    public Emprestimo solicitarEmprestimo(String numeroContaCliente, String idGrupo, double valorSolicitado) {
        // Regra: valor do empréstimo não pode ser maior que a soma dos últimos 2 meses de poupança
        double somaUltimos2Meses = calcularSomaUltimosDoisMesesPoupanca(numeroContaCliente);
        
        if (valorSolicitado > somaUltimos2Meses) {
            return null; // Valor solicitado maior que o permitido
        }
        
        // Verificar se cliente tem saldo para pagar? (opcional)
        Cliente cliente = gestaoCliente.buscarClientePorNumeroConta(numeroContaCliente);
        if (cliente == null) {
            return null;
        }
        
        String id = UUID.randomUUID().toString();
        Emprestimo emprestimo = new Emprestimo(id, numeroContaCliente, idGrupo, valorSolicitado);
        emprestimo.setStatus(Emprestimo.StatusEmprestimo.ATIVO);
        emprestimos.add(emprestimo);
        
        // Adicionar valor ao saldo do cliente
        cliente.adicionarSaldo(valorSolicitado);
        
        salvarEmprestimos();
        return emprestimo;
    }
    
    // READ
    public Emprestimo buscarEmprestimoPorId(String id) {
        for (Emprestimo emprestimo : emprestimos) {
            if (emprestimo.getId().equals(id)) {
                emprestimo.verificarAtraso();
                return emprestimo;
            }
        }
        return null;
    }
    
    public List<Emprestimo> listarEmprestimosPorCliente(String numeroContaCliente) {
        List<Emprestimo> resultado = new ArrayList<>();
        for (Emprestimo emprestimo : emprestimos) {
            if (emprestimo.getNumeroContaCliente().equals(numeroContaCliente)) {
                emprestimo.verificarAtraso();
                resultado.add(emprestimo);
            }
        }
        return resultado;
    }
    
    public List<Emprestimo> listarEmprestimosPorGrupo(String idGrupo) {
        List<Emprestimo> resultado = new ArrayList<>();
        for (Emprestimo emprestimo : emprestimos) {
            if (emprestimo.getIdGrupo() != null && emprestimo.getIdGrupo().equals(idGrupo)) {
                emprestimo.verificarAtraso();
                resultado.add(emprestimo);
            }
        }
        return resultado;
    }
    
    public List<Emprestimo> listarEmprestimosEmAtraso() {
        List<Emprestimo> atrasados = new ArrayList<>();
        for (Emprestimo emprestimo : emprestimos) {
            emprestimo.verificarAtraso();
            if (emprestimo.getStatus() == Emprestimo.StatusEmprestimo.EM_ATRASO) {
                atrasados.add(emprestimo);
            }
        }
        return atrasados;
    }
    
    // UPDATE - Pagamento
    public boolean pagarEmprestimo(String idEmprestimo, double valor) {
        Emprestimo emprestimo = buscarEmprestimoPorId(idEmprestimo);
        if (emprestimo == null) {
            return false;
        }
        
        Cliente cliente = gestaoCliente.buscarClientePorNumeroConta(emprestimo.getNumeroContaCliente());
        if (cliente == null) {
            return false;
        }
        
        // Verificar se cliente tem saldo para pagar
        double valorDivida = emprestimo.getValorEmDivida();
        double valorPagar = Math.min(valor, valorDivida);
        
        if (cliente.removerSaldo(valorPagar)) {
            boolean pagou = emprestimo.pagar(valorPagar);
            if (pagou) {
                // Distribuir os juros pagos
                double jurosPagos = calcularJurosPagos(emprestimo, valorPagar);
                distribuirJuros(emprestimo.getIdGrupo(), jurosPagos);
            }
            salvarEmprestimos();
            return pagou;
        }
        return false;
    }
    
    // Calcular quanto dos juros foi pago
    private double calcularJurosPagos(Emprestimo emprestimo, double valorPago) {
        double valorOriginalComJuros = emprestimo.getValorComJuros();
        double valorAtual = emprestimo.calcularValorAtualComJuros();
        
        // Simplificação: proporção dos juros no valor pago
        if (valorAtual <= valorOriginalComJuros) {
            return 0; // Sem juros adicionais
        }
        
        double totalJuros = valorAtual - emprestimo.getValorSolicitado();
        double proporcaoPaga = valorPago / valorAtual;
        return totalJuros * proporcaoPaga;
    }
    
    // Distribuição dos juros: 50% empresa, 50% membros (quem tem maior saldo ganha mais)
    private void distribuirJuros(String idGrupo, double jurosPagos) {
        if (jurosPagos <= 0 || idGrupo == null) {
            return;
        }
        
        double parteEmpresa = jurosPagos * 0.5;
        double parteMembros = jurosPagos * 0.5;
        
        // 50% para a empresa (simulação: adicionar ao saldo total do grupo)
        gestaoGrupo.atualizarSaldoTotalGrupo(idGrupo, parteEmpresa);
        
        // 50% para os membros - quem tem maior saldo na poupança ganha mais
        List<Poupanca> poupancasGrupo = gestaoPoupanca.listarPoupancasPorGrupo(idGrupo);
        
        // Calcular total de saldo em poupança do grupo
        double totalSaldoPoupancas = 0;
        for (Poupanca p : poupancasGrupo) {
            if (p.getStatus() == Poupanca.StatusPoupanca.ATIVA) {
                totalSaldoPoupancas += p.getValorInvestido();
            }
        }
        
        // Distribuir proporcionalmente ao saldo de cada membro
        for (Poupanca p : poupancasGrupo) {
            if (p.getStatus() == Poupanca.StatusPoupanca.ATIVA && totalSaldoPoupancas > 0) {
                double proporcao = p.getValorInvestido() / totalSaldoPoupancas;
                double bonusMembro = parteMembros * proporcao;
                
                Cliente cliente = gestaoCliente.buscarClientePorNumeroConta(p.getNumeroContaCliente());
                if (cliente != null) {
                    cliente.adicionarSaldo(bonusMembro);
                }
            }
        }
    }
    
    // RELATÓRIOS
    public double getTotalEmprestimosPorGrupo(String idGrupo) {
        double total = 0;
        for (Emprestimo e : listarEmprestimosPorGrupo(idGrupo)) {
            total += e.getValorSolicitado();
        }
        return total;
    }
    
    public double getTotalDividaAtivaPorCliente(String numeroContaCliente) {
        double total = 0;
        for (Emprestimo e : listarEmprestimosPorCliente(numeroContaCliente)) {
            if (e.getStatus() != Emprestimo.StatusEmprestimo.PAGO) {
                total += e.getValorEmDivida();
            }
        }
        return total;
    }
}