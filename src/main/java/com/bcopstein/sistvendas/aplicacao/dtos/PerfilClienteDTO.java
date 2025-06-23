package com.bcopstein.sistvendas.aplicacao.dtos;

import java.util.List;

public class PerfilClienteDTO {
    private String nomeCliente;
    private int nroOrcamentos;
    private List<OrcamentoDTO> orcamentos;

    public PerfilClienteDTO(String nomeCliente, List<OrcamentoDTO> orcamentos) {
        this.nomeCliente = nomeCliente;
        this.orcamentos = orcamentos;
        this.nroOrcamentos = orcamentos.size();
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public int getNroOrcamentos() {
        return nroOrcamentos;
    }

    public List<OrcamentoDTO> getOrcamentos() {
        return orcamentos;
    }
} 