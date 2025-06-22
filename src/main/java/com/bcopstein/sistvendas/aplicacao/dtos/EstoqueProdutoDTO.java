package com.bcopstein.sistvendas.aplicacao.dtos;

public class EstoqueProdutoDTO {

    private long idProduto;
    private String descricao;
    private int quantidade;

    public EstoqueProdutoDTO(long idProduto, String descricao, int quantidade) {
        this.idProduto = idProduto;
        this.descricao = descricao;
        this.quantidade = quantidade;
    }

    public long getIdProduto() {
        return idProduto;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getQuantidade() {
        return quantidade;
    }

}
