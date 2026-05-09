/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
//package mind7trader.model;

package model;
import java.time.LocalDate;

import java.time.LocalDate;

public class Poupanca {

    private String numeroConta;
    private double valor;
    private double taxa;
    private LocalDate data;

    public Poupanca(String numeroConta, double valor,
                     double taxa, LocalDate data) {

        this.numeroConta = numeroConta;
        this.valor = valor;
        this.taxa = taxa;
        this.data = data;
    }

    public String getNumeroConta() {
        return numeroConta;
    }

    public double getValor() {
        return valor;
    }
 
    public double getTaxa() {
        return taxa;
    }

    public LocalDate getData() {
        return data;
    }
}  