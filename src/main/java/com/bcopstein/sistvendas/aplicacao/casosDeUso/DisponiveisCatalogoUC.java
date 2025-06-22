package com.bcopstein.sistvendas.aplicacao.casosDeUso;

import java.util.List;
import java.util.stream.Collector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bcopstein.sistvendas.aplicacao.dtos.EstoqueProdutoDTO;
import com.bcopstein.sistvendas.dominio.modelos.ProdutoModel;
import com.bcopstein.sistvendas.dominio.persistencia.IEstoqueRepositorio;
import com.bcopstein.sistvendas.dominio.persistencia.IProdutoRepositorio;

@Component
public class DisponiveisCatalogoUC {

    private IProdutoRepositorio produtoRepositorio;
    private IEstoqueRepositorio estoqueRepositorio;
    
    @Autowired
    public DisponiveisCatalogoUC(IProdutoRepositorio produtoRepositorio, IEstoqueRepositorio estoqueRepositorio) {
        this.produtoRepositorio = produtoRepositorio;
        this.estoqueRepositorio = estoqueRepositorio;
    }

    public List<EstoqueProdutoDTO> run() {
        List<ProdutoModel> produtos = produtoRepositorio.todos();
        
        return produtos.stream()
            .map(prod -> new EstoqueProdutoDTO(
                prod.getId(), 
                prod.getDescricao(), 
                        estoqueRepositorio.quantidadeEmEstoque(prod.getId()) >= 0
                    ?   estoqueRepositorio.quantidadeEmEstoque(prod.getId())
                    : 0
                ))
                .toList();
    }

    


}
