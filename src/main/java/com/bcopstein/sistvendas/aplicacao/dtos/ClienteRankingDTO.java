package com.bcopstein.sistvendas.aplicacao.dtos;

public class ClienteRankingDTO {
    private String nomeCliente;
    private double valorTotalComprado;
    private int quantidadeCompras;

    public ClienteRankingDTO(String nomeCliente, double valorTotalComprado, int quantidadeCompras) {
        this.nomeCliente = nomeCliente;
        this.valorTotalComprado = valorTotalComprado;
        this.quantidadeCompras = quantidadeCompras;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public double getValorTotalComprado() {
        return valorTotalComprado;
    }

    public int getQuantidadeCompras() {
        return quantidadeCompras;
    }
} 