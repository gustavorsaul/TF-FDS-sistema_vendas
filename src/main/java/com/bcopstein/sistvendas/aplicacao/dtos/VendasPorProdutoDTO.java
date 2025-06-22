package com.bcopstein.sistvendas.aplicacao.dtos;

public class VendasPorProdutoDTO {
    private long produtoId;
    private String descricao;
    private int quantidadeVendida;
    private double valorTotal;
 
    public VendasPorProdutoDTO(long produtoId,String descricao, int quantidadeVendida, double valorTotal){
        this.produtoId = produtoId;
        this.descricao = descricao;
        this.quantidadeVendida =quantidadeVendida;
        this.valorTotal = valorTotal;
    }
    public long getProdutoId() {
        return produtoId;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getQuantidadeVendida() {
        return quantidadeVendida;
    }

    public double getValorTotal() {
        return valorTotal;
    }
}

