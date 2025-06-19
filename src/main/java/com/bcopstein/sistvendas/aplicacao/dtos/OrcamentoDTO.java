package com.bcopstein.sistvendas.aplicacao.dtos;

import java.util.LinkedList;
import java.util.List;
import java.time.LocalDate;

import com.bcopstein.sistvendas.dominio.modelos.ItemPedidoModel;
import com.bcopstein.sistvendas.dominio.modelos.OrcamentoModel;

public class OrcamentoDTO {
    private long id;
    private String nomeCliente;
    private String pais;
    private String estado;
    private List<ItemPedidoDTO> itens;
    private double custoItens;
    private double impostoEstadual;
    private double impostoFederal;
    private double desconto;
    private double custoConsumidor;
    private LocalDate dataCriacao;
    private LocalDate validade;
    private boolean efetivado;

    public OrcamentoDTO(OrcamentoModel orcamento) {
        this.id = orcamento.getId();
        this.nomeCliente = orcamento.getNomeCliente();
        this.pais = orcamento.getPais();
        this.estado = orcamento.getEstado();
        this.custoItens = orcamento.getCustoItens();
        this.impostoEstadual = orcamento.getImpostoEstadual();
        this.impostoFederal = orcamento.getImpostoFederal();
        this.desconto = orcamento.getDesconto();
        this.custoConsumidor = orcamento.getCustoConsumidor();
        this.dataCriacao = orcamento.getDataCriacao();
        this.validade = orcamento.getValidade();
        this.efetivado = orcamento.isEfetivado();

        itens = new LinkedList<>();
        for (ItemPedidoModel item : orcamento.getItens()) {
            itens.add(new ItemPedidoDTO(item.getProduto().getId(), item.getQuantidade()));
        }
    }

    public long getId() {
        return id;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public String getPais() {
        return pais;
    }

    public String getEstado() {
        return estado;
    }

    public List<ItemPedidoDTO> getItens(){
        return itens;
    }

    public double getCustoItens() {
        return custoItens;
    }


    public double getImpostoEstadual() {
        return impostoEstadual;
    }

    public double getImpostoFederal() {
        return impostoFederal;
    }

    public double getDesconto() {
        return desconto;
    }

    public double getCustoConsumidor() {
        return custoConsumidor;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public LocalDate getValidade() {
        return validade;
    }

    public boolean isEfetivado() {
        return efetivado;
    }

    public void efetiva(){
        efetivado = true;
    }

    public static OrcamentoDTO fromModel(OrcamentoModel orcamento){
        return new OrcamentoDTO(orcamento);
    }
}
