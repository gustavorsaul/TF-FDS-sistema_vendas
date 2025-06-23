package com.bcopstein.sistvendas.aplicacao.dtos;

import java.util.List;

public class PerfilComprasClienteDTO {
    private String nomeCliente;
    private double totalGasto;
    private int quantidadeCompras;
    private List<ProdutoCompradoDTO> produtosComprados;

    public PerfilComprasClienteDTO(String nomeCliente, double totalGasto, int quantidadeCompras, List<ProdutoCompradoDTO> produtosComprados) {
        this.nomeCliente = nomeCliente;
        this.totalGasto = totalGasto;
        this.quantidadeCompras = quantidadeCompras;
        this.produtosComprados = produtosComprados;
    }

    public String getNomeCliente() { return nomeCliente; }
    public double getTotalGasto() { return totalGasto; }
    public int getQuantidadeCompras() { return quantidadeCompras; }
    public List<ProdutoCompradoDTO> getProdutosComprados() { return produtosComprados; }

    public static class ProdutoCompradoDTO {
        private long produtoId;
        private String descricao;
        private int quantidade;
        private double valorTotal;

        public ProdutoCompradoDTO(long produtoId, String descricao, int quantidade, double valorTotal) {
            this.produtoId = produtoId;
            this.descricao = descricao;
            this.quantidade = quantidade;
            this.valorTotal = valorTotal;
        }

        public long getProdutoId() { return produtoId; }
        public String getDescricao() { return descricao; }
        public int getQuantidade() { return quantidade; }
        public double getValorTotal() { return valorTotal; }
    }
} 