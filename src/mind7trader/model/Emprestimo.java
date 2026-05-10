 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
//package mind7trader.model;
package model;
/**
 *
 * @author HP
 */
//package model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.io.Serializable;

public class Emprestimo implements Serializable {
    
    private static final double TAXA_JUROS_MENSAL = 0.20; // 20% ao mês
    
    private String id;
    private String numeroContaCliente;
    private String idGrupo;
    private double valorSolicitado;
    private double valorComJuros;
    private double valorPago;
    private LocalDate dataEmprestimo;
    private LocalDate dataVencimento;
    private StatusEmprestimo status;
    private int mesesAtraso;
    
    public enum StatusEmprestimo {
        PENDENTE,
        ATIVO,
        PAGO,
        EM_ATRASO
    }
    
    public Emprestimo(String id, String numeroContaCliente, String idGrupo, double valorSolicitado) {
        this.id = id;
        this.numeroContaCliente = numeroContaCliente;
        this.idGrupo = idGrupo;
        this.valorSolicitado = valorSolicitado;
        this.dataEmprestimo = LocalDate.now();
        this.dataVencimento = dataEmprestimo.plusMonths(1);
        this.valorComJuros = valorSolicitado + (valorSolicitado * TAXA_JUROS_MENSAL);
        this.valorPago = 0;
        this.status = StatusEmprestimo.PENDENTE;
        this.mesesAtraso = 0;
    }
    
    public double calcularValorAtualComJuros() {
        if (status == StatusEmprestimo.PAGO) {
            return valorPago;
        }
        
        LocalDate hoje = LocalDate.now();
        double valorBase = valorComJuros;
        
        if (hoje.isAfter(dataVencimento)) {
            long mesesAtrasoCalculado = ChronoUnit.MONTHS.between(dataVencimento, hoje);
            if (mesesAtrasoCalculado > 0) {
                double valorAtual = valorComJuros;
                for (int i = 0; i < mesesAtrasoCalculado; i++) {
                    valorAtual += valorAtual * TAXA_JUROS_MENSAL;
                }
                return valorAtual;
            }
        }
        
        return valorBase;
    }
    
    public double getValorEmDivida() {
        double valorAtual = calcularValorAtualComJuros();
        return valorAtual - valorPago;
    }
    
    public boolean pagar(double valor) {
        double dividaAtual = getValorEmDivida();
        if (valor <= 0 || valor > dividaAtual) {
            return false;
        }
        
        this.valorPago += valor;
        
        if (this.valorPago >= calcularValorAtualComJuros()) {
            this.status = StatusEmprestimo.PAGO;
            this.valorPago = calcularValorAtualComJuros();
        }
        
        return true;
    }
    
    public void verificarAtraso() {
        LocalDate hoje = LocalDate.now();
        if (status != StatusEmprestimo.PAGO && hoje.isAfter(dataVencimento)) {
            long atraso = ChronoUnit.MONTHS.between(dataVencimento, hoje);
            if (atraso > 0) {
                this.mesesAtraso = (int) atraso;
                this.status = StatusEmprestimo.EM_ATRASO;
            }
        }
    }
    
    // Getters e Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getNumeroContaCliente() { return numeroContaCliente; }
    public void setNumeroContaCliente(String numeroContaCliente) { this.numeroContaCliente = numeroContaCliente; }
    
    public String getIdGrupo() { return idGrupo; }
    public void setIdGrupo(String idGrupo) { this.idGrupo = idGrupo; }
    
    public double getValorSolicitado() { return valorSolicitado; }
    public void setValorSolicitado(double valorSolicitado) { this.valorSolicitado = valorSolicitado; }
    
    public double getValorComJuros() { return valorComJuros; }
    public void setValorComJuros(double valorComJuros) { this.valorComJuros = valorComJuros; }
    
    public double getValorPago() { return valorPago; }
    public void setValorPago(double valorPago) { this.valorPago = valorPago; }
    
    public LocalDate getDataEmprestimo() { return dataEmprestimo; }
    public void setDataEmprestimo(LocalDate dataEmprestimo) { this.dataEmprestimo = dataEmprestimo; }
    
    public LocalDate getDataVencimento() { return dataVencimento; }
    public void setDataVencimento(LocalDate dataVencimento) { this.dataVencimento = dataVencimento; }
    
    public StatusEmprestimo getStatus() { return status; }
    public void setStatus(StatusEmprestimo status) { this.status = status; }
    
    public int getMesesAtraso() { return mesesAtraso; }
    public void setMesesAtraso(int mesesAtraso) { this.mesesAtraso = mesesAtraso; }
    
    public static double getTaxaJurosMensal() {
        return TAXA_JUROS_MENSAL;
    }
    
    @Override
    public String toString() {
        return "Emprestimo{" +
                "id='" + id + '\'' +
                ", cliente='" + numeroContaCliente + '\'' +
                ", valorSolicitado=" + valorSolicitado +
                ", divida=" + getValorEmDivida() +
                ", vencimento=" + dataVencimento +
                '}';
    }
}
