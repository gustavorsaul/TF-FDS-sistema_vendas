package com.bcopstein.sistvendas.aplicacao.dtos;

import java.util.List;

public class ListaIdProdutoDTO {

    private List<Long> idsProdutos;

    public ListaIdProdutoDTO() {
    }

    public ListaIdProdutoDTO(List<Long> idProduto) {
        this.idsProdutos = idProduto;
    }

    public List<Long> getIdsProdutos() {
        return idsProdutos;
    }

    public void setIdsProdutos(List<Long> idProduto) {
        this.idsProdutos = idProduto;
    }

    

}
