package com.bcopstein.sistvendas.aplicacao.dtos;

public class ClienteComprasDTO {
    private String nomeCliente;
    private double totalGasto;

    public ClienteComprasDTO(String nomeCliente, double totalGasto) {
        this.nomeCliente = nomeCliente;
        this.totalGasto = totalGasto;
    }

    public String getNomeCliente() { return nomeCliente; }
    public double getTotalGasto() { return totalGasto; }
} 