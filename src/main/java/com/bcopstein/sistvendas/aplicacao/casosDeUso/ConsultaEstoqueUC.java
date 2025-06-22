package com.bcopstein.sistvendas.aplicacao.casosDeUso;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bcopstein.sistvendas.aplicacao.dtos.EstoqueDTO;
import com.bcopstein.sistvendas.dominio.modelos.ItemDeEstoqueModel;
import com.bcopstein.sistvendas.dominio.servicos.ServicoDeEstoque;

@Component
public class ConsultaEstoqueUC {
    private final ServicoDeEstoque servicoEstoque;

    @Autowired
    public ConsultaEstoqueUC(ServicoDeEstoque servicoEstoque) {
        this.servicoEstoque = servicoEstoque;
    }

    public List<EstoqueDTO> run() {
        List<ItemDeEstoqueModel> itens = servicoEstoque.itensDeEstoque();

        return itens.stream()
            .map(item -> new EstoqueDTO(
                item.getProduto().getId(),
                item.getProduto().getDescricao(),
                item.getQuantidade()
            ))
            .collect(Collectors.toList());
    }
}
