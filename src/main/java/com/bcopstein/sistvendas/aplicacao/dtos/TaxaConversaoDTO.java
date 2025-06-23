package com.bcopstein.sistvendas.aplicacao.dtos;

public class TaxaConversaoDTO {
    private int totalOrcamentos;
    private int efetivados;
    private double taxaConversao;

    public TaxaConversaoDTO(int totalOrcamentos, int efetivados, double taxaConversao) {
        this.totalOrcamentos = totalOrcamentos;
        this.efetivados = efetivados;
        this.taxaConversao = taxaConversao;
    }

    public int getTotalOrcamentos() {
        return totalOrcamentos;
    }

    public int getEfetivados() {
        return efetivados;
    }

    public double getTaxaConversao() {
        return taxaConversao;
    }
}
