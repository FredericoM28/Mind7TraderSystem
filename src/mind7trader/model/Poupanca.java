/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
//package mind7trader.model;

package model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.io.Serializable;

public class Poupanca implements Serializable {
    
    private static final double TAXA_POUPANCA = 0.50; // 50% fixo
    
    private String id;
    private String numeroContaCliente;
    private String idGrupo;
    private double valorInvestido;
    private double valorTotalComJuros;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private TipoCiclo ciclo;
    private TipoPeriodo periodo;
    private StatusPoupanca status;
    
    public enum TipoCiclo {
        SEIS_MESES(6),
        NOVE_MESES(9),
        DOZE_MESES(12);
        
        private final int meses;
        
        TipoCiclo(int meses) {
            this.meses = meses;
        }
        
        public int getMeses() {
            return meses;
        }
    }
    
    public enum TipoPeriodo {
        SEMANAL,
        MENSAL
    }
    
    public enum StatusPoupanca {
        ATIVA,
        CONCLUIDA,
        CANCELADA
    }
    
    public Poupanca(String id, String numeroContaCliente, String idGrupo, 
                    double valorInvestido, TipoCiclo ciclo, TipoPeriodo periodo) {
        this.id = id;
        this.numeroContaCliente = numeroContaCliente;
        this.idGrupo = idGrupo;
        this.valorInvestido = valorInvestido;
        this.ciclo = ciclo;
        this.periodo = periodo;
        this.dataInicio = LocalDate.now();
        this.dataFim = dataInicio.plusMonths(ciclo.getMeses());
        this.valorTotalComJuros = calcularValorFinal();
        this.status = StatusPoupanca.ATIVA;
    }
    
    private double calcularValorFinal() {
        return valorInvestido + (valorInvestido * TAXA_POUPANCA);
    }
    
    public double calcularLucro() {
        return valorTotalComJuros - valorInvestido;
    }
    
    public boolean isConcluida() {
        return LocalDate.now().isAfter(dataFim) || LocalDate.now().isEqual(dataFim);
    }
    
    public long getDiasRestantes() {
        if (isConcluida()) return 0;
        return ChronoUnit.DAYS.between(LocalDate.now(), dataFim);
    }
    
    // Getters e Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getNumeroContaCliente() { return numeroContaCliente; }
    public void setNumeroContaCliente(String numeroContaCliente) { this.numeroContaCliente = numeroContaCliente; }
    
    public String getIdGrupo() { return idGrupo; }
    public void setIdGrupo(String idGrupo) { this.idGrupo = idGrupo; }
    
    public double getValorInvestido() { return valorInvestido; }
    public void setValorInvestido(double valorInvestido) { this.valorInvestido = valorInvestido; }
    
    public double getValorTotalComJuros() { return valorTotalComJuros; }
    public void setValorTotalComJuros(double valorTotalComJuros) { this.valorTotalComJuros = valorTotalComJuros; }
    
    public LocalDate getDataInicio() { return dataInicio; }
    public void setDataInicio(LocalDate dataInicio) { this.dataInicio = dataInicio; }
    
    public LocalDate getDataFim() { return dataFim; }
    public void setDataFim(LocalDate dataFim) { this.dataFim = dataFim; }
    
    public TipoCiclo getCiclo() { return ciclo; }
    public void setCiclo(TipoCiclo ciclo) { this.ciclo = ciclo; }
    
    public TipoPeriodo getPeriodo() { return periodo; }
    public void setPeriodo(TipoPeriodo periodo) { this.periodo = periodo; }
    
    public StatusPoupanca getStatus() { return status; }
    public void setStatus(StatusPoupanca status) { this.status = status; }
    
    public static double getTaxaPoupanca() {
        return TAXA_POUPANCA;
    }
    
    @Override
    public String toString() {
        return "Poupanca{" +
                "id='" + id + '\'' +
                ", cliente='" + numeroContaCliente + '\'' +
                ", valorInvestido=" + valorInvestido +
                ", valorFinal=" + valorTotalComJuros +
                ", dataFim=" + dataFim +
                '}';
    }
}