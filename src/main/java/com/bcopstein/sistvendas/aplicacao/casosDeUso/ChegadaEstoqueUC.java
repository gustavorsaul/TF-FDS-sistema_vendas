package com.bcopstein.sistvendas.aplicacao.casosDeUso;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bcopstein.sistvendas.aplicacao.dtos.ItemPedidoDTO;
import com.bcopstein.sistvendas.dominio.modelos.ItemDeEstoqueModel;
import com.bcopstein.sistvendas.dominio.persistencia.IEstoqueRepositorio;
import com.bcopstein.sistvendas.dominio.modelos.ItemDeEstoqueModel;

@Component
public class ChegadaEstoqueUC {

    private IEstoqueRepositorio estoqueRepositorio;

    @Autowired
    public ChegadaEstoqueUC(IEstoqueRepositorio estoqueRepositorio) {
        this.estoqueRepositorio = estoqueRepositorio;
    }

    public ItemDeEstoqueModel run(ItemPedidoDTO item) {
        return estoqueRepositorio
            .adicionaEstoque
                (item.getIdProduto(), item.getQtdade());
    }

    


}
