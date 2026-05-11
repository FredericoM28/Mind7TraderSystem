/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
//package mind7trader. model;
package model;

import java.io.Serializable;

public class Cliente implements Serializable {

    private String numeroConta;
    private String nome;
    private String bi;
    private String telefone;
    private String email;
    private String morada;
    private double saldo;
    private String senha;
    private boolean ativo;
    private int idade;

    // construtor vazio 
    public Cliente() {
    }
    
    // cronstrutor da classe cliente
    public Cliente(String numeroConta, String nome, String bi, String telefone,
                   String email, String morada, double saldo, String senha, boolean ativo) {
        this.numeroConta = numeroConta;
        this.nome = nome;
        this.bi = bi;
        this.telefone = telefone;
        this.email = email;
        this.morada = morada;
        this.saldo = saldo;
        this.senha = senha;
        this.ativo = ativo;
    }

    // Getters e Setters

    public String getNumeroConta() { return numeroConta; }
    public void setNumeroConta(String numeroConta) { this.numeroConta = numeroConta; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getBi() { return bi; }
    public void setBi(String bi) { this.bi = bi; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getMorada() { return morada; }
    public void setMorada(String morada) { this.morada = morada; }

    public double getSaldo() { return saldo; }
    public void setSaldo(double saldo) { this.saldo = saldo; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }

    public void adicionarSaldo(double valor) {
        this.saldo += valor;
    }

    public boolean removerSaldo(double valor) {
        if (this.saldo >= valor) {
            this.saldo -= valor;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return numeroConta + ";" + nome + ";" + bi + ";" + telefone + ";" + email + ";" + saldo;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }
}