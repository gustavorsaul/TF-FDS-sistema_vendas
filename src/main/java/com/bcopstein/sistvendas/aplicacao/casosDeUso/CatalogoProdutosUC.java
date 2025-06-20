package com.bcopstein.sistvendas.aplicacao.casosDeUso;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bcopstein.sistvendas.aplicacao.dtos.ProdutoDTO;
import com.bcopstein.sistvendas.dominio.servicos.ServicoDeProduto;

@Component
public class CatalogoProdutosUC {

    private ServicoDeProduto servicoProduto;

    @Autowired
    public CatalogoProdutosUC(ServicoDeProduto servicoProduto) {
        this.servicoProduto = servicoProduto;
    }

    public List<ProdutoDTO> run() {
        return servicoProduto.todosProdutos().stream()
            .map(p->ProdutoDTO.fromModel(p))
            .toList();
    }
    

}
