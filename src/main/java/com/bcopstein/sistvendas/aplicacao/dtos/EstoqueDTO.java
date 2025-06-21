package com.bcopstein.sistvendas.aplicacao.dtos;

public class EstoqueDTO {
    private long produtoId;
    private String descricao;
    private int quantidadeDisponivel;

    public EstoqueDTO(long produtoId, String descricao, int quantidadeDisponivel){
    this.produtoId = produtoId;
    this.descricao = descricao;
    this.quantidadeDisponivel = quantidadeDisponivel;

}
public long getProdutoId() {
    return produtoId;
}

public String getDescricao() {
    return descricao;
}

public int getQuantidadeDisponivel() {
    return quantidadeDisponivel;
}
}