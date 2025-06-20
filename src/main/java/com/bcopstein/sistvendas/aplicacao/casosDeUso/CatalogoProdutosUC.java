package com.bcopstein.sistvendas.aplicacao.casosDeUso;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bcopstein.sistvendas.aplicacao.dtos.ProdutoDTO;
import com.bcopstein.sistvendas.dominio.persistencia.IProdutoRepositorio;
import com.bcopstein.sistvendas.dominio.servicos.ServicoDeProduto;

@Component
public class CatalogoProdutosUC {

    private ServicoDeProduto servicoDeProduto;

    @Autowired
    public CatalogoProdutosUC(ServicoDeProduto servicoDeProduto) {
        this.servicoDeProduto = servicoDeProduto;
    }

    public List<ProdutoDTO> run() {
        return servicoDeProduto.todosProdutos().stream()
            .map(p->ProdutoDTO.fromModel(p))
            .toList();
    }
    

}
