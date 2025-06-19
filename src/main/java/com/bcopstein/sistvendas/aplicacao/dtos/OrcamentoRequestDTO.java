package com.bcopstein.sistvendas.aplicacao.dtos;

import java.util.List;

public class OrcamentoRequestDTO {
    private String nomeCliente;
    private String pais;
    private String estado;
    private List<ItemPedidoDTO> itens;

    public OrcamentoRequestDTO(String nomeCliente, String pais, String estado, List<ItemPedidoDTO> itens) {
        this.nomeCliente = nomeCliente;
        this.pais = pais;
        this.estado = estado;
        this.itens = itens;
    }

    protected OrcamentoRequestDTO() {}

    public String getNomeCliente() {
        return nomeCliente;
    }

    public String getPais() {
        return pais;
    }

    public String getEstado() {
        return estado;
    }

    public List<ItemPedidoDTO> getItens() {
        return itens;
    }

    @Override
    public String toString() {
        return "OrcamentoRequestDTO [nomeCliente=" + nomeCliente + ", pais=" + pais + ", estado=" + estado 
                + ", itens=" + itens + "]";
    }
}
