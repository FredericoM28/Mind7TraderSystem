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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Grupo implements Serializable {
    
    private String id;
    private String nome;
    private TipoCiclo ciclo;
    private TipoPeriodo periodo;
    private StatusGrupo status;
    private List<String> idsMembros;
    private List<String> idsPoupancas;
    private List<String> idsEmprestimos;
    private double saldoTotalGrupo;
    private double saldoTotal;
    
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
    
    public enum StatusGrupo {
        ATIVO,
        FINALIZADO,
        CANCELADO
    }
    
    public Grupo(String id, String nome, TipoCiclo ciclo, TipoPeriodo periodo) {
        this.id = id;
        this.nome = nome;
        this.ciclo = ciclo;
        this.periodo = periodo;
        this.status = StatusGrupo.ATIVO;
        this.idsMembros = new ArrayList<>();
        this.idsPoupancas = new ArrayList<>();
        this.idsEmprestimos = new ArrayList<>();
        this.saldoTotalGrupo = 0;
    }
    
    public void adicionarMembro(String idCliente) {
        if (!idsMembros.contains(idCliente)) {
            idsMembros.add(idCliente);
        }
    }
    
    public void removerMembro(String idCliente) {
        idsMembros.remove(idCliente);
    }
    
    public void adicionarPoupanca(String idPoupanca) {
        idsPoupancas.add(idPoupanca);
    }
    
    public void adicionarEmprestimo(String idEmprestimo) {
        idsEmprestimos.add(idEmprestimo);
    }
    
    public void atualizarSaldoTotal(double valor) {
        this.saldoTotalGrupo += valor;
    }

    public void adicionarSaldoTotal(double valor) {
    this.saldoTotal += valor;
}
    
    public int getQuantidadeMembros() {
        return idsMembros.size();
    }
    
    // Getters e Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    public TipoCiclo getCiclo() { return ciclo; }
    public void setCiclo(TipoCiclo ciclo) { this.ciclo = ciclo; }
    
    public TipoPeriodo getPeriodo() { return periodo; }
    public void setPeriodo(TipoPeriodo periodo) { this.periodo = periodo; }
    
    public StatusGrupo getStatus() { return status; }
    public void setStatus(StatusGrupo status) { this.status = status; }
    
    public List<String> getIdsMembros() { return idsMembros; }
    public void setIdsMembros(List<String> idsMembros) { this.idsMembros = idsMembros; }
    
    public List<String> getIdsPoupancas() { return idsPoupancas; }
    public void setIdsPoupancas(List<String> idsPoupancas) { this.idsPoupancas = idsPoupancas; }
    
    public List<String> getIdsEmprestimos() { return idsEmprestimos; }
    public void setIdsEmprestimos(List<String> idsEmprestimos) { this.idsEmprestimos = idsEmprestimos; }
    
    public double getSaldoTotalGrupo() { return saldoTotalGrupo; }
    public void setSaldoTotalGrupo(double saldoTotalGrupo) { this.saldoTotalGrupo = saldoTotalGrupo; }
    
    @Override
    public String toString() {
        return "Grupo{" +
                "id='" + id + '\'' +
                ", nome='" + nome + '\'' +
                ", ciclo=" + ciclo +
                ", periodo=" + periodo +
                ", membros=" + idsMembros.size() +
                ", saldoTotal=" + saldoTotalGrupo +
                '}';
    }

    public double getSaldoTotal() {
        return saldoTotal;
    }

    public void setSaldoTotal(double saldoTotal) {
        this.saldoTotal = saldoTotal;
    }
}
