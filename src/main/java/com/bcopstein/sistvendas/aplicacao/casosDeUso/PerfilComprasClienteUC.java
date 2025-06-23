package com.bcopstein.sistvendas.aplicacao.casosDeUso;

import com.bcopstein.sistvendas.aplicacao.dtos.OrcamentoDTO;
import com.bcopstein.sistvendas.aplicacao.dtos.PerfilClienteDTO;
import com.bcopstein.sistvendas.dominio.servicos.ServicoDeVendas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class PerfilComprasClienteUC {
    private final ServicoDeVendas servicoDeVendas;

    @Autowired
    public PerfilComprasClienteUC(ServicoDeVendas servicoDeVendas) {
        this.servicoDeVendas = servicoDeVendas;
    }

    public PerfilClienteDTO run(String nomeCliente) {
        return new PerfilClienteDTO(
                nomeCliente,
                servicoDeVendas.getOrcamentosPorCliente(nomeCliente)
                        .stream()
                        .map(OrcamentoDTO::fromModel)
                        .collect(Collectors.toList())
        );
    }
} 