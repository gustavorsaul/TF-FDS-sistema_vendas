package com.bcopstein.sistvendas.dominio.modelos;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class OrcamentoModel {
    private long id;
    private String nomeCliente;
    private String pais;
    private String estado;
    private LocalDate dataCriacao;
    private LocalDate validade;

    private List<ItemPedidoModel> itens = new LinkedList<>();
    private double custoItens;
    private double impostoEstadual;
    private double impostoFederal;
    private double desconto;
    private double custoConsumidor;
    private boolean efetivado;
    private LocalDate data;


    public OrcamentoModel(long id) {
        this.id = id;
        this.itens = new LinkedList<>();
        this.efetivado = false;
    }

    public OrcamentoModel() {
        this.itens = new LinkedList<>();
        this.efetivado = false;
    }

    public void configurarOrcamento(String nomeCliente, String pais, String estado) {
        this.nomeCliente = nomeCliente;
        this.pais = pais;
        this.estado = estado;
        this.dataCriacao = LocalDate.now();
        this.validade = dataCriacao.plusDays(21);
    }

    public boolean estaValido() {
        return LocalDate.now().isBefore(validade) || LocalDate.now().isEqual(validade);
    }

    public void addItensPedido(ItemPedidoModel item) {
        itens.add(item);
    }

    public void addItensPedido(PedidoModel pedido) {
        for (ItemPedidoModel itemPedido : pedido.getItens()) {
            itens.add(itemPedido);
        }
    }

    public List<ItemPedidoModel> getItens() {
        return itens;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public LocalDate getValidade() {
        return validade;
    }

    public double getCustoItens() {
        return custoItens;
    }

    public void setCustoItens(double custoItens) {
        this.custoItens = custoItens;
    }

    public double getImpostoEstadual() {
        return impostoEstadual;
    }

    public void setImpostoEstadual(double impostoEstadual) {
        this.impostoEstadual = impostoEstadual;
    }

    public double getImpostoFederal() {
        return impostoFederal;
    }

    public void setImpostoFederal(double impostoFederal) {
        this.impostoFederal = impostoFederal;
    }

    public double getDesconto() {
        return desconto;
    }

    public void setDesconto(double desconto) {
        this.desconto = desconto;
    }

    public double getCustoConsumidor() {
        return custoConsumidor;
    }

    public void setCustoConsumidor(double custoConsumidor) {
        this.custoConsumidor = custoConsumidor;
    }

    public boolean isEfetivado() {
        return efetivado;
    }

    public void efetiva() {
        efetivado = true;
    }
    public double valorFinal() {
    return custoItens + imposto - desconto;
}
    public LocalDate getData() {
         return data;
}

}
