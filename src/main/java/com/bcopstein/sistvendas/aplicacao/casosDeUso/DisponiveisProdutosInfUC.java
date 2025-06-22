package com.bcopstein.sistvendas.aplicacao.casosDeUso;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bcopstein.sistvendas.aplicacao.dtos.EstoqueProdutoDTO;
import com.bcopstein.sistvendas.dominio.servicos.ServicoDeEstoque;

@Component
public class DisponiveisProdutosInfUC {

    ServicoDeEstoque servicoDeEstoque;

    @Autowired
    public DisponiveisProdutosInfUC(ServicoDeEstoque servicoDeEstoque) {
        this.servicoDeEstoque = servicoDeEstoque;
    }



    public List<EstoqueProdutoDTO> run(List<Long> lista) {
        return servicoDeEstoque.disponiveisListaInformada(lista);
    }
}
