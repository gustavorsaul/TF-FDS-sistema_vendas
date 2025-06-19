package com.bcopstein.sistvendas.dominio.servicos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcopstein.sistvendas.dominio.modelos.ItemPedidoModel;
import com.bcopstein.sistvendas.dominio.modelos.LocalizacaoModel;
import com.bcopstein.sistvendas.dominio.modelos.OrcamentoModel;
import com.bcopstein.sistvendas.dominio.modelos.PedidoModel;
import com.bcopstein.sistvendas.dominio.modelos.ProdutoModel;
import com.bcopstein.sistvendas.dominio.persistencia.IEstoqueRepositorio;
import com.bcopstein.sistvendas.dominio.persistencia.IOrcamentoRepositorio;

@Service
public class ServicoDeVendas {
    private IOrcamentoRepositorio orcamentos;
    private IEstoqueRepositorio estoque;

    @Autowired
    public ServicoDeVendas(IOrcamentoRepositorio orcamentos, IEstoqueRepositorio estoque) {
        this.orcamentos = orcamentos;
        this.estoque = estoque;
    }

    public OrcamentoModel criaOrcamento(PedidoModel pedido, String nomeCliente, String pais, String estado) {
        // Validação de localização
        LocalizacaoModel localizacao = new LocalizacaoModel(pais, estado);
        if (!localizacao.localAtendido()) {
            throw new IllegalArgumentException("Localização não atendida: " + pais + "-" + estado);
        }

        // Cria orçamento
        OrcamentoModel novoOrcamento = new OrcamentoModel();
        novoOrcamento.configurarOrcamento(nomeCliente, pais, estado);
        novoOrcamento.addItensPedido(pedido);

        // Cálculo dos custos dos itens
        double custoItens = novoOrcamento.getItens().stream()
                .mapToDouble(it -> it.getProduto().getPrecoUnitario() * it.getQuantidade())
                .sum();
        novoOrcamento.setCustoItens(custoItens);

        // Cálculo de impostos
        double impostoEstadual = calculaImpostoEstadual(pais, estado, custoItens);
        double impostoFederal = calculaImpostoFederal(pais, custoItens);
        novoOrcamento.setImpostoEstadual(impostoEstadual);
        novoOrcamento.setImpostoFederal(impostoFederal);

        // Cálculo de descontos
        double descontoPorItem = calculaDescontoPorItem(novoOrcamento.getItens());
        double descontoPorVolume = calculaDescontoPorVolume(novoOrcamento.getItens());
        double descontoTotal = descontoPorItem + descontoPorVolume;
        novoOrcamento.setDesconto(descontoTotal);

        // Custo final
        double custoConsumidor = custoItens + impostoEstadual + impostoFederal - descontoTotal;
        novoOrcamento.setCustoConsumidor(custoConsumidor);

        // Persiste
        return this.orcamentos.cadastra(novoOrcamento);
    }

    public OrcamentoModel efetivaOrcamento(long id) {
        var orcamento = this.orcamentos.recuperaPorId(id);
        if (orcamento == null || orcamento.isEfetivado()) {
            throw new IllegalArgumentException("Orçamento inexistente ou já efetivado");
        }

        if (!orcamento.estaValido()) {
            throw new IllegalStateException("Orçamento expirado. Validade até: " + orcamento.getValidade());
        }

        var ok = true;
        for (ItemPedidoModel itemPedido : orcamento.getItens()) {
            int qtdade = estoque.quantidadeEmEstoque(itemPedido.getProduto().getId());
            if (qtdade < itemPedido.getQuantidade()) {
                ok = false;
                break;
            }
        }

        if (ok) {
            for (ItemPedidoModel itemPedido : orcamento.getItens()) {
                estoque.baixaEstoque(itemPedido.getProduto().getId(), itemPedido.getQuantidade());
            }
            orcamentos.marcaComoEfetivado(id);
        }

        return orcamentos.recuperaPorId(id);
    }

    public OrcamentoModel buscaOrcamento(long idOrcamento) {
        return this.orcamentos.recuperaPorId(idOrcamento);
    }

    // =====================
    // 🔥 Métodos auxiliares
    // =====================

    private double calculaImpostoEstadual(String pais, String estado, double base) {
        if (pais.equalsIgnoreCase("Brasil")) {
            switch (estado.toUpperCase()) {
                case "RS":
                    return base * 0.10;
                case "SP":
                    return base * 0.12;
                case "RJ":
                    return base * 0.15;
                default:
                    return base * 0.10; // Default se não especificado
            }
        }
        return base * 0.20; // Outros países
    }

    private double calculaImpostoFederal(String pais, double base) {
        if (pais.equalsIgnoreCase("Brasil")) {
            return base * 0.05;
        }
        return base * 0.10; // Outros países
    }

    private double calculaDescontoPorItem(List<ItemPedidoModel> itens) {
        return itens.stream()
                .filter(it -> it.getQuantidade() > 3)
                .mapToDouble(it -> it.getProduto().getPrecoUnitario() * it.getQuantidade() * 0.05)
                .sum();
    }

    private double calculaDescontoPorVolume(List<ItemPedidoModel> itens) {
        int totalItens = itens.stream().mapToInt(ItemPedidoModel::getQuantidade).sum();
        if (totalItens > 10) {
            double valorTotal = itens.stream()
                    .mapToDouble(it -> it.getProduto().getPrecoUnitario() * it.getQuantidade())
                    .sum();
            return valorTotal * 0.10;
        }
        return 0.0;
    }
}
